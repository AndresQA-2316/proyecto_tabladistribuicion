import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class FrmTablaDistribucion extends JFrame {

    JComboBox cmbRespuesta;
    JList lstMuestra;
    String[] opciones = new String[] { "Excelente", "Buena", "Regular", "Mala" };
    String[] encabezado = new String[]{"Variable", 
    "Frecuencia absoluta (f)",
    "Frecuencia acumulada (F)",
    "Frecuencia relativa (fr)",
    "Frecuencia porcentual (%f)"};
    JTable tblTablaDistribucion;

    public FrmTablaDistribucion() {
        setSize(600, 500);
        setTitle("Tabla deDistribución");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JTextArea txtPregunta = new JTextArea(
                "¿Cómo considera la calidad de la señal de internet que entra al barrio? ");
        txtPregunta.setBounds(10, 10, 250, 50);
        txtPregunta.setEditable(false);
        txtPregunta.setOpaque(false);
        txtPregunta.setLineWrap(true);
        getContentPane().add(txtPregunta);

        cmbRespuesta = new JComboBox();
        DefaultComboBoxModel mdlRespuesta = new DefaultComboBoxModel(opciones);
        cmbRespuesta.setModel(mdlRespuesta);
        cmbRespuesta.setBounds(10, 60, 100, 25);
        getContentPane().add(cmbRespuesta);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(80, 90, 100, 25);
        getContentPane().add(btnAgregar);

        JButton btnQuitar = new JButton("Quitar");
        btnQuitar.setBounds(80, 120, 100, 25);
        getContentPane().add(btnQuitar);

        lstMuestra = new JList();
        JScrollPane spMuestra = new JScrollPane(lstMuestra);
        spMuestra.setBounds(210, 50, 100, 150);
        getContentPane().add(spMuestra);

        JButton btnTablaDistribucion = new JButton("Calcular");
        btnTablaDistribucion.setBounds(10, 200, 100, 25);
        getContentPane().add(btnTablaDistribucion);

        //agregar tabla
        tblTablaDistribucion=new JTable();
        JScrollPane spTablaDistribucion = new JScrollPane(tblTablaDistribucion);
        spTablaDistribucion.setBounds(10,230,500,200);
        getContentPane().add(spTablaDistribucion);

        //Asignar los datos a la tabla
        DefaultTableModel dtm = new DefaultTableModel(null, encabezado);
        tblTablaDistribucion.setModel(dtm);

        // eventos de la GUI
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                agregarDato();
            }
        });

        btnQuitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                quitarDato();
            }
        });

        btnTablaDistribucion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                calcularTablaDistribucion();
            }
        });
    }

    // declarar el arreglo que almacenará los datos de la muestra
    private String[] muestra = new String[1000];
    private int totalDatos = -1;

    private void agregarDato() {

            String respuesta = opciones[cmbRespuesta.getSelectedIndex()];
            totalDatos++;
            muestra[totalDatos] = respuesta;
            mostrarMuestra();

    }

    private void mostrarMuestra() {
        String[] strMuestra = new String[totalDatos + 1];
        for (int i = 0; i <= totalDatos; i++) {
            strMuestra[i] = muestra[i];
        }
        lstMuestra.setListData(strMuestra);
    }

    private void quitarDato() {
        // obtener la posicion escogida
        int posicion = lstMuestra.getSelectedIndex();
        if (posicion >= 0) {
            // retirar la posicion del vector
            for (int i = posicion; i < totalDatos; i++) {
                muestra[i] = muestra[i + 1];
            }
            totalDatos--;
            mostrarMuestra();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una posición");
        }
    }

    private void calcularTablaDistribucion(){
        double[][] tablaDistribucion=new double[opciones.length][4];
        for(int i=0; i<=totalDatos; i++){
            //buscar la posicion de la variable
            int posicion=-1;
            for(int j=0; j<opciones.length;j++){
                if (muestra[i].equals(opciones[j])) {
                    posicion = j;
                    break;
                }
            }

            //incrementar la frecuencia absoluta
            tablaDistribucion[posicion][0]++;
        }
        
         //calcular frecuencia acumulada
         tablaDistribucion[0][1]=tablaDistribucion[0][0];
         for(int i = 1; i < opciones.length; i++){
             tablaDistribucion[i][1]=tablaDistribucion[i-1][1]+tablaDistribucion[i][0];

         }

       // mostrar la tabla de frecuencias
       String[][] strTablasDistribucion=new String[opciones.length][5];
       for(int i=0;i<opciones.length;i++){

        tablaDistribucion[i][2]=tablaDistribucion[i][0] / tablaDistribucion[opciones.length-1][1];
        tablaDistribucion[i][3]=tablaDistribucion[i][2] * 100;

           strTablasDistribucion[i][0]=opciones[i];
           strTablasDistribucion[i][1]=String.valueOf(tablaDistribucion[i][0]);
           strTablasDistribucion[i][2]=String.valueOf(tablaDistribucion[i][1]);
           strTablasDistribucion[i][3]=String.valueOf(tablaDistribucion[i][2]);
           strTablasDistribucion[i][4]=String.valueOf(tablaDistribucion[i][3]);
       }

       
       //Asignar los datos a la tabla
       DefaultTableModel dtm = new DefaultTableModel(strTablasDistribucion, encabezado);
       tblTablaDistribucion.setModel(dtm);
    }
}
