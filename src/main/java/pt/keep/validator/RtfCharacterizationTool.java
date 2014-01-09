package pt.keep.validator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import pt.keep.validator.result.Result;
import pt.keep.validator.result.ValidationInfo;

import com.rtfparserkit.parser.RtfStreamSource;
import com.rtfparserkit.parser.standard.StandardRtfParser;
import com.rtfparserkit.utils.RtfDump;

public class RtfCharacterizationTool {
	private static String version = "1.0";

	public String getVersion(){
		return version;
	}
	public String run(File f) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Result res = process(f);
			JAXBContext jaxbContext = JAXBContext.newInstance(Result.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(res, bos);
			return bos.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Result process(File f) {
		Result res = new Result();
		try {
			StandardRtfParser srp = new StandardRtfParser();
			InputStream is = new FileInputStream(f);
			OutputStream os = new ByteArrayOutputStream();
			srp.parse( new RtfStreamSource(is), new RtfDump(os));
			ValidationInfo validationInfo = new ValidationInfo();
			validationInfo.setValid(true);
			res.setValidationInfo(validationInfo);
		} catch (Exception e) {
		  if(e.getMessage().equalsIgnoreCase("Unsupported encoding command ansicpg1250")){    //Hack...
		    ValidationInfo validationInfo = new ValidationInfo();
            validationInfo.setValid(true);
            res.setValidationInfo(validationInfo);
		  }else{
			ValidationInfo validationInfo = new ValidationInfo();
			validationInfo.setValid(false);
			validationInfo.setValidationError(e.getMessage());
			res.setValidationInfo(validationInfo);
		  }
		}

		return res;
	}

	


	private void printHelp(Options opts) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar [jarFile]", opts);
	}
	
	private void printVersion() {
		System.out.println(version);
	}
	public static void main(String[] args) {
		try {
			RtfCharacterizationTool rct = new RtfCharacterizationTool();
			Options options = new Options();
			options.addOption("f", true, "file to analyze");
			options.addOption("v", false, "print this tool version");
			options.addOption("h", false, "print this message");

			CommandLineParser parser = new GnuParser();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("h")) {
				rct.printHelp(options);
				System.exit(0);
			}
			
			if (cmd.hasOption("v")) {
				rct.printVersion();
				System.exit(0);
			}

			if (!cmd.hasOption("f")) {
				rct.printHelp(options);
				System.exit(0);
			}

			File f = new File(cmd.getOptionValue("f"));
			if (!f.exists()) {
				System.out.println("File doesn't exist");
				System.exit(0);
			}
			String toolOutput = rct.run(f);
			if(toolOutput!=null){
				System.out.println(toolOutput);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
