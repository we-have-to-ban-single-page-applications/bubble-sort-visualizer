import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.util.SystemInfo
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    if (SystemInfo.isLinux) JFrame.setDefaultLookAndFeelDecorated(true)
    FlatLightLaf.setup()

    SwingUtilities.invokeLater {
        JFrame("Bubble sort visualizer").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(800, 600)
            setLocationRelativeTo(null)
            contentPane = ContentPanel()
            isVisible = true
        }
    }
}
