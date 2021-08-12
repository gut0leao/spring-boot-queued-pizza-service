package tk.gutoleao.springbootqueuedpizzaservice.enums;

public enum EnumOrderStatus {
    
    AWAITING("Awaiting"),
	PREPARING("Preparing"),
    DELIVERING("Delivering"),
    DELIVERED("Delivered"),
    ERROR("Service Error"),
    CANCELED("Canceled");

	public final String description;

	private EnumOrderStatus(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
