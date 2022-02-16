package exist

import javax.swing.JFrame
import java.awt.EventQueue
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JTable
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JScrollPane
import java.awt.FlowLayout
import org.w3c.dom.Element
import javax.xml.xquery.XQDataSource
import javax.xml.xquery.XQConnection
import javax.xml.xquery.XQResultSequence
import net.xqj.exist.ExistXQDataSource
import javax.xml.xquery.XQConstants
import kotlin.system.exitProcess

class T9Ex7_Rutes_XML_eXist : JFrame() {
    val s = ExistXQDataSource()
    var conn: XQConnection
    var rs: XQResultSequence? = null
    var ruta : Element? = null

    val etiqueta = JLabel("")
    val etNom = JLabel("Ruta:")
    val qNom = JTextField(15)
    val etDesn = JLabel("Desnivell:")
    val qDesn = JTextField(5)
    val etDesnAcum = JLabel("Desnivell acumulat:")
    val qDesnAcum = JTextField(5)
    val etPunts = JLabel("Punts:")
    val punts = JTable(1, 3)
    val primer = JButton("<<")
    val anterior = JButton("<")
    val seguent = JButton(">")
    val ultim = JButton(">>")
    val tancar = JButton("Tancar")

    init {
        getContentPane().setLayout(GridLayout(0, 1))
        val p_prin = JPanel()
        s.setProperty("serverName", "89.36.214.106")

        conn = s.getConnection()
        p_prin.setLayout(BoxLayout(p_prin, BoxLayout.Y_AXIS))
        val cntxt = conn.getStaticContext()
        cntxt.setScrollability(XQConstants.SCROLLTYPE_SCROLLABLE)
        conn.setStaticContext(cntxt)
        val panell1 = JPanel(GridLayout(0, 2))
        panell1.add(etNom)
        qNom.setEditable(false)
        panell1.add(qNom)
        panell1.add(etDesn)
        qDesn.setEditable(false)
        panell1.add(qDesn)
        panell1.add(etDesnAcum)
        qDesnAcum.setEditable(false)
        panell1.add(qDesnAcum)
        panell1.add(etPunts)

        val panell2 = JPanel(GridLayout(0, 1))
        punts.setEnabled(false)
        val scroll = JScrollPane(punts)
        panell2.add(scroll, null)

        val panell5 = JPanel(FlowLayout())
        panell5.add(primer)
        panell5.add(anterior)
        panell5.add(seguent)
        panell5.add(ultim)
        panell5.add(tancar)

        getContentPane().add(p_prin)
        p_prin.add(panell1)
        p_prin.add(panell2)
        p_prin.add(panell5)

        setVisible(true)
        pack()

        primer.addActionListener() {
            rs!!.first()
            ruta = rs!!.`object` as Element
            VisRuta()
        }

        anterior.addActionListener() {
            rs!!.previous()
            ruta = rs!!.`object` as Element
            VisRuta()
        }

        seguent.addActionListener() {
            rs!!.next()
            ruta = rs!!.`object` as Element
            VisRuta()
        }

        ultim.addActionListener() {
            rs!!.last()
            ruta = rs!!.`object` as Element
            VisRuta()
        }

        tancar.addActionListener() {
            conn.close()
            exitProcess(0)
        }
        inicialitzar()
        VisRuta()
    }

    fun plenar_taula(e_punts: Element) {
        val ll_punts = e_punts.getElementsByTagName("punt")
        val ll = Array(ll_punts.getLength()) { Array(3) { "" } }
        for (i in 0..ll_punts.getLength() - 1) {
            val p = ll_punts.item(i) as Element
            ll[i][0] = p.getElementsByTagName("nom").item(0).getFirstChild().getNodeValue()
            ll[i][1] = p.getElementsByTagName("latitud").item(0).getFirstChild().getNodeValue()
            ll[i][2] = p.getElementsByTagName("longitud").item(0).getFirstChild().getNodeValue()
        }
        val caps = arrayOf<String>("Nom punt", "Latitud", "Longitud")
        punts.setModel(javax.swing.table.DefaultTableModel(ll, caps))
    }

    fun inicialitzar() {
        rs = conn.prepareExpression("//rutes/ruta").executeQuery()
        rs!!.first()
        ruta = rs!!.`object` as Element
    }

    fun VisRuta() {
        seguent.isEnabled = !rs!!.isLast
        anterior.isEnabled = !rs!!.isFirst
        qNom.text = ruta!!.getElementsByTagName("nom").item(0).firstChild.nodeValue
        qDesn.text = ruta!!.getElementsByTagName("desnivell").item(0).firstChild.nodeValue
        qDesnAcum.text = ruta!!.getElementsByTagName("desnivellAcumulat").item(0).firstChild.nodeValue
        plenar_taula(ruta!!.getElementsByTagName("punts").item(0) as Element)


    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        T9Ex7_Rutes_XML_eXist().isVisible = true
    }
}