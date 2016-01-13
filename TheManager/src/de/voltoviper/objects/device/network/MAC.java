package de.voltoviper.objects.device.network;

public class MAC {
	
	String mac;
	
	public MAC(String notconverted){
		this.mac=macconverter(notconverted);
	}

	private String macconverter(String notconverted) {
		StringBuilder builder = new StringBuilder();
		if(!notconverted.isEmpty()){
			if(notconverted.length()==12){
				for (int i=0;i<=notconverted.length();i++){
					builder.append(notconverted.charAt(i));
					i++;
					builder.append(notconverted.charAt(i));
					builder.append(":");
				}
			}
		}
		return builder.toString();
	}

	public String getMac() {
		return mac;
	}

}
