package classesPrincipais;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import classesPrincipais.Cliente;
import dados.*;
import view.*;
import dao.*;

public class UdiDecola_App extends JFrame {
    private DadosCliente dadosCliente;
    private DadosFuncionarios dadosFuncionario;
    
    public UdiDecola_App() {
        super("UdiDecola App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(562, 287);

        dadosCliente = new DadosCliente();
        dadosFuncionario = new DadosFuncionarios();
        
        // Crio a tabela, se nao existir
        ClienteDAO.criarTabelaCliente();

        JButton abrirJanelaButton = new JButton("Abrir Interface de Cadastro de Cliente");
        abrirJanelaButton.setBounds(92, 31, 379, 32);
        abrirJanelaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInterfaceCadastroCliente();
            }
        });
        
        JButton abrirJanelaFuncionarioButton = new JButton("Abrir Interface de Cadastro de Funcionário");
        abrirJanelaFuncionarioButton.setBounds(102, 74, 363, 32); // Ajuste do posicionamento
        abrirJanelaFuncionarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInterfaceCadastroFuncionario();
            }
        });



        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(abrirJanelaButton);
        getContentPane().add(panel);
        
   
        panel.add(abrirJanelaFuncionarioButton);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    
    private void abrirInterfaceCadastroFuncionario() {
        InterfaceCadastroFuncionario interfaceCadastroFuncionario = new InterfaceCadastroFuncionario(this,dadosFuncionario);
        interfaceCadastroFuncionario.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Se chegou aqui, a janela já foi fechada.
            	atualizarExibicaoFuncionarios(); 
   

            }
        });
    }
    
    public void atualizarExibicaoFuncionarios() {
        System.out.println("Lista de funcionarios atualizada:");
        dadosFuncionario.listar();
    }
    
    // ------- Aplicações de CLIENTE -------
    private void abrirInterfaceCadastroCliente() {
        InterfaceCadastroCliente interfaceCadastroCliente = new InterfaceCadastroCliente(this, dadosCliente);
        interfaceCadastroCliente.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	// Se chegou aqui, a janela já foi fechada.
                atualizarExibicaoClientes();           
                salvarBancoDados();
                salvarArquivos();
              
            }
        });
    }
    
    public void salvarArquivos() {
        try {
            int ultimoIndice = dadosCliente.getVetClientes().size() - 1;

            // Verificar se há pelo menos um cliente na lista
            if (ultimoIndice >= 0) {
                // Obter o último cliente da lista
                Cliente ultimoCliente = dadosCliente.getVetClientes().get(ultimoIndice);
                
                // Escrever as informações do cliente em um arquivo
                String path = "ArqClientes.txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(path,true))) {
                    bw.write(ultimoCliente.dadosArquivo()); // Aqui você pode ajustar o formato de saída conforme necessário
                    System.out.println("Informações do cliente salvas no arquivo: " + path);
                } catch (IOException e) {
                    // Lidar com exceção de E/S
                    System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
                }
            } else {
                System.out.println("Não há clientes na lista para salvar em arquivo.");
            }
        } catch (Exception e) {
            // Lidar com exceções gerais
            System.err.println("Erro ao salvar informações em arquivo: " + e.getMessage());
            e.printStackTrace(); // Imprimir rastreamento da pilha para depuração
        }
    }
    
    
    public void salvarBancoDados() {
        try {
            int ultimoIndice = dadosCliente.getVetClientes().size() - 1;

            // Verificar se há pelo menos um cliente na lista
            if (ultimoIndice >= 0) {
                // Obter o último cliente da lista
                Cliente ultimoCliente = dadosCliente.getVetClientes().get(ultimoIndice);
                
                // Usar o ClienteDAO para inserir o último cliente no banco de dados
                ClienteDAO.inserirCliente(ultimoCliente);
            } else {
                System.out.println("Não há clientes na lista para inserir.");
            }
        } catch (Exception e) {
            // Lidar com a exceção
            System.err.println("Erro ao salvar no banco de dados: " + e.getMessage());
            e.printStackTrace(); // Isso imprimirá o rastreamento da pilha para ajudar na depuração
        }
    }

    
    public void atualizarExibicaoClientes() {
        System.out.println("Lista de clientes atualizada:");
        dadosCliente.listar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UdiDecola_App();
            }
        });
    }
}