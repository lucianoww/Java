 /*
 * Criado por Luciano Wachholz
 * Ano: 2019
 * twitter: lucianow76
 * 
 * Criado para praticar conhecimentos adquiridos na linguagem java
 * 
 * Proximas versões:  - Aplicação de novos recursos;
 *                    - Trabalhar com arquivo properties;
 *                    - Corrigir bugs;
 *                    - Evoluir para MVC;
 */

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MiniEditorJava extends JFrame 
{
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea txtArea;
	
	private JLabel lblAplicativo;
	private JLabel lblVersao;
	private JLabel lblFonte;
	private JLabel lblTamanho;
	private JLabel lblEstilo;
	private JLabel lblAutor;
    			
	private JComboBox<String> novaFonte;
	private JComboBox<Integer> novoTamanho;
	private JComboBox<String> novoEstilo;
	
	private JTextField tfAutor;
	private JTextField tfPensamentos;
	private JTextField txtArquivo;
	private JTextArea txtTexto;
	
	private Integer codigoEstilo;
	private String[] fonteDisponivel;
	private Font areaFonte;
	private Integer tamFonte;
	private Integer tamanhoLimite=35;
	private Integer tempo=7;
	private ArrayList<Integer> tamanhoPermitido;
	private List <String> pensamento;
	
	private JFileChooser arq;
	FileNameExtensionFilter extensoesPermitidas;
    ComponentOrientation posicao = ComponentOrientation.LEFT_TO_RIGHT;
    

	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public MiniEditorJava() throws FileNotFoundException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 783, 219);
		contentPane.add(scrollPane);
		scrollPane.setComponentOrientation(posicao);
		
		txtArea = new JTextArea();
		txtArea.setEditable(true); 	
		scrollPane.setViewportView(txtArea);
		
		lblAplicativo = new JLabel("Mini-Editor Java");
		lblAplicativo.setFont(new Font("Ravie", Font.PLAIN, 25));
		lblAplicativo.setBounds(10, 0, 311, 59);
		contentPane.add(lblAplicativo);
		
		lblAutor = new JLabel("Autor: Luciano Wachholz");
		lblAutor.setBounds(10, 45, 137, 14);
		contentPane.add(lblAutor);
		
        lblVersao = new JLabel("Vers\u00E3o 1.0");
		lblVersao.setBounds(245, 42, 67, 14);
		contentPane.add(lblVersao);
		
		contentPane.add(scrollPane);
		scrollPane.setViewportView(txtArea);
		
		//Legenda Fontes
		lblFonte = new JLabel("Fonte");
		lblFonte.setBounds(400, 13, 47, 14);
		contentPane.add(lblFonte);
	
		//Monta combo Fontes
		//Carrega Fontes disponiveis
		novaFonte = new JComboBox();
		novaFonte.setEditable(false); //nao deixa editar
		novaFonte.setBounds(399, 28, 205, 22);
		contentPane.add(novaFonte);
		loadFontes();

		
		//Legenda Tamanho
		lblTamanho = new JLabel("Tamanho");
		lblTamanho.setBounds(737, 13, 67, 14);
		contentPane.add(lblTamanho);
		
		//Carrega Tamanho da Fonte
		loadTamanhoFonte();
		novoTamanho = new JComboBox(tamanhoPermitido.toArray());
		novoTamanho.setBounds(737, 28, 56, 22);
		contentPane.add(novoTamanho);
		
		
		//Legenda Estilo
		lblEstilo = new JLabel("Estilo");
		lblEstilo.setBounds(614, 13, 108, 14);
		contentPane.add(lblEstilo);
		
		novoEstilo = new JComboBox();
		novoEstilo.setEditable(false);
		novoEstilo.setBounds(614, 28, 113, 22);
		contentPane.add(novoEstilo);
		loadEstilo();
		
		//Adiciona Botoes na Janela
		addBotoes();
		
		//Padrao de fontes		
		defaultFonte();
		
		//Pensamentos
		getPensamentos();
		
		//Monitora alteracao na combo Fonte
		novaFonte.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				int estiloAtual=tipoEstilo();
				txtArea.setFont(new Font((String) novaFonte.getSelectedItem(),estiloAtual, (Integer) novoTamanho.getSelectedItem()));
			}
		});
		
		//Monitora alteracao na combo Estilo
		novoEstilo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				int estiloAtual=tipoEstilo();
				txtArea.setFont(new Font((String) novaFonte.getSelectedItem(),estiloAtual, (Integer) novoTamanho.getSelectedItem()));
			}
		});
		
		//Monitora alteracao na combo Tamano 
		novoTamanho.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				int estiloAtual=tipoEstilo();
				txtArea.setFont(new Font((String) novaFonte.getSelectedItem(),estiloAtual, (Integer) novoTamanho.getSelectedItem()));
			}
		});
		
		//Atualiza pensamentos aleatorios
		ActionListener atualizaPensamentos = new ActionListener() 
		{  
		    public void actionPerformed(ActionEvent ev){
		    	runPensamentos();
		    }
		};  
		
		Integer cron= (int) (tempo * Math.pow(10, 3));
		Timer timer = new Timer(cron, atualizaPensamentos);  
		timer.setRepeats(true);  
		timer.start();
	}
	
	public void addBotoes(){
		
		//Implementando botao Abrir
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				cleanArea();  //Limpa area de texto
				opnArquivo();
			}
		});
		btnAbrir.setBounds(10, 300, 89, 23);
		contentPane.add(btnAbrir);
		
		//Implementando botao Salvar
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e){
				wrtArquivo();
			}
		});
		btnSalvar.setBounds(115, 300, 89, 23);
		contentPane.add(btnSalvar);
		
		//Implementando botao de Saída
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSair.setBounds(225, 300, 89, 23);
		contentPane.add(btnSair);
		
		tfPensamentos = new JTextField();
		tfPensamentos.setFont(new Font("Verdana", Font.ITALIC, 12));
		tfPensamentos.setHorizontalAlignment(SwingConstants.TRAILING);
		tfPensamentos.setEditable(false);
		tfPensamentos.setBounds(340, 319, 453, 22);
		tfPensamentos.setBorder(null);
		contentPane.add(tfPensamentos);
		
		tfAutor = new JTextField();
		tfAutor.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 12));
		tfAutor.setHorizontalAlignment(SwingConstants.TRAILING);
		tfAutor.setEditable(false);
		tfAutor.setBounds(534, 299, 259, 22);
		tfAutor.setBorder(null);
		contentPane.add(tfAutor);
	}
	
	//Limpa a textarea para carregar o novo arquivo na tela;
	public void cleanArea()	{
	    txtArea.setText("");
	}
	
	//Grava o arquivo 
	public void wrtArquivo(){
		try{
			File arquivo;
			arq= new JFileChooser();
			arq.setAcceptAllFileFilterUsed(false);
			arq.addChoosableFileFilter(new FileNameExtensionFilter("Somente Texto", "txt", "log", "csv"));
			arq.setSelectedFile(new File("Mini-Editor Java - xxx"));
			int Result=arq.showSaveDialog(this);
			if(Result==JFileChooser.APPROVE_OPTION)
			{
				arquivo = arq.getSelectedFile();
				FileWriter inArq = new FileWriter(arquivo.getPath());
				inArq.write(txtArea.getText()); 
				inArq.close();
			}
		}catch(IOException ioe) {
		}
	}
		
	//Abre o arquivo, le arquivo, adiciona linha por linha na area de texto e encerra buffer de leitura;
	public void opnArquivo() {
			File arquivo;
			arq= new JFileChooser();
			arq.setAcceptAllFileFilterUsed(false);
			arq.addChoosableFileFilter(new FileNameExtensionFilter("Somente Texto", "txt", "log", "csv"));

			
		    if (arq.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		        return;
		        
		    StringBuilder texto = new StringBuilder();

		    try {
		        Scanner scan = new Scanner(arq.getSelectedFile());
		        while (scan.hasNextLine()) {
		            texto.append(scan.nextLine()).append("\n");
		        }
		        txtArea.append(texto.toString());
		        
		      scan.close();

		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(this, "Não foi possível ler o arquivo selecionado.");
		        e.printStackTrace();
		    }
	}
	
	//Carrega para combo as fontes disponiveis
	public void loadFontes() {
         GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 String[] fonts = e.getAvailableFontFamilyNames(); // Get the fonts
		 for (String fnts : fonts) 
			 novaFonte.addItem(fnts);
	}
	
	//Define Fonte, Estilo e tamanho Default
	public void defaultFonte() {
		novaFonte.setSelectedItem("Courier New");
		novoEstilo.setSelectedItem("Normal");
		novoTamanho.setSelectedItem(20);
	}
	
	//Tamanho de Fontes selecionaveis
	public void loadTamanhoFonte(){
		tamanhoPermitido = new ArrayList<Integer>();
		int tam = 8;
		do{
			tamanhoPermitido.add(tam);
			tam++;
		}while (tam <= tamanhoLimite);
	}
	
	//Define Estilos selecionaveis
	public void loadEstilo() {
		novoEstilo.addItem("Normal");
		novoEstilo.addItem("Bold");
		novoEstilo.addItem("Italic");
		novoEstilo.addItem("Bold and Italic");
	}
	
	//Recupera o codigo do tipo de estilo
	public int tipoEstilo()   {
        codigoEstilo = Font.PLAIN;
        if(novoEstilo.getSelectedItem().equals("Bold"))
        {
            codigoEstilo = Font.BOLD;
        }
        else if(novoEstilo.getSelectedItem().equals("Italic"))
        {
        	codigoEstilo = Font.ITALIC;
        }
        else if(novoEstilo.getSelectedItem().equals("Bold and Italic"))
        {
        	codigoEstilo = Font.BOLD|Font.ITALIC;
        }
        return codigoEstilo;
    }
	
	//Lê arquivo de pensamentos
	public void getPensamentos() throws FileNotFoundException {
		
		
	    FileInputStream meuarq = new FileInputStream("./Textos/Pensamentos.txt");
	    Scanner sc = new Scanner(meuarq);
	    
	    pensamento = new ArrayList<String>();
	    
		while (sc.hasNextLine()) {
			pensamento.add(sc.nextLine());
		}
	}
	
	public void runPensamentos() {
		
		int numeroLinhas=0;
		int posPensamento=0;
		String linhaAtual="";
		int posDelimitador=0;
		String txtAutor="";
		String txtPensamento="";
		
		//Obtem numero de elementos do arraylist
		numeroLinhas=pensamento.size()-1;
				
		Random rdn = new Random();
		posPensamento = rdn.nextInt(numeroLinhas);
		
		System.out.printf("\nlinha %d", posPensamento); 
		linhaAtual = pensamento.get(posPensamento).toString();
		
		posDelimitador = linhaAtual.indexOf(";");
		
		txtAutor = linhaAtual.substring(0, posDelimitador);
		txtPensamento = linhaAtual.substring(posDelimitador+1, linhaAtual.length());
		
		tfAutor.setText(txtAutor);
		tfPensamentos.setText("\""+txtPensamento+"\"");
	}
        
	
	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiniEditorJava frame = new MiniEditorJava();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
