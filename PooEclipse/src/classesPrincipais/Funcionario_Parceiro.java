package classesPrincipais;

import java.util.ArrayList;

public class Funcionario_Parceiro extends Funcionario {

	private ArrayList<Hotel> hoteis_cadastro;
	private static float porcentagem_por_hotel;
	private Cliente cliente;

	public Funcionario_Parceiro(String cpf, String endereco, String nome, Data data_nasc, String nro_carteira, Hotel hotel,Cliente cliente) {
		super(cpf, endereco, nome, data_nasc, nro_carteira);
		Funcionario_Parceiro.setPorcentagem_por_hotel(0.05f);
		hoteis_cadastro = new ArrayList<Hotel>();
		this.cliente = cliente;
		addHotel(hotel);
	}
	
	
	public Funcionario_Parceiro(String cpf, String endereco, String nome, Data data_nasc, String nro_carteira,
			Cliente cliente) {
		super(cpf, endereco, nome, data_nasc, nro_carteira);
		this.cliente = cliente;
	}


	@Override
	public String toString() {
		return "Funcionario_Parceiro [cliente=" + cliente.toString() + "]";
	}


	public float CalculaSalario() {
		return (super.getSalario() + (super.getSalario() * getPorcentagem_por_hotel() * getHoteis_cadastro().size()));
	}

	public void addHotel(Hotel hotel) {
		this.getHoteis_cadastro().add(hotel);
		hotel.setFuncionario(this);
	}

	public ArrayList<Hotel> getHoteis_cadastro() {
		return hoteis_cadastro;
	}

	public void setHoteis_cadastro(ArrayList<Hotel> hoteis_cadastro) {
		this.hoteis_cadastro = hoteis_cadastro;
	}

	public static float getPorcentagem_por_hotel() {
		return porcentagem_por_hotel;
	}

	public static void setPorcentagem_por_hotel(float porcentagem_por_hotel) {
		Funcionario_Parceiro.porcentagem_por_hotel = porcentagem_por_hotel;
	}

}
