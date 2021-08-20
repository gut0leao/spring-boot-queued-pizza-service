package tk.gutoleao.springbootqueuedpizzaservice.enums;

public enum EnumPizzaMenu {

	NAPOLETANA("Napoletana",
			"No disappointments, the traditional and fast Napolitan pizza. Only with a default process delay."),
	QUATRO_FORMAGIO("Quatro FormaggIO", "The interruptive four-cheese pizza packed with in-and-out operations."),
	FIBONACCI("Fibonacci the Parma", "With CPU Bound operations to the customer's taste.");

	public final String itemName;
	public final String description;

	private EnumPizzaMenu(String itemName, String description) {
		this.itemName = itemName;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
