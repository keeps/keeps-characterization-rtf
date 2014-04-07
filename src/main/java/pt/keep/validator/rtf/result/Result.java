package pt.keep.validator.rtf.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "rtfCharacterizationResult")
@XmlType(propOrder = { "validationInfo"})
public class Result {

	
	private ValidationInfo validationInfo;
	
	

	

	@XmlElement(required=true)
	public ValidationInfo getValidationInfo() {
		return validationInfo;
	}

	public void setValidationInfo(ValidationInfo validationInfo) {
		this.validationInfo = validationInfo;
	}
	
	
	

}
