import java.awt.Color
import java.awt.Graphics
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.UIManager

class GraphicsPanel(
    private val elements: Elements,
) : JPanel() {
    val mainColor: Color = UIManager.getColor("Component.accentColor") ?: Color.BLUE
    val activeColor: Color = UIManager.getColor("Component.focusColor") ?: Color.LIGHT_GRAY
    val inactiveColor: Color = UIManager.getColor("Component.borderColor") ?: Color.DARK_GRAY

    fun createImage() =
        BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).also { image ->
            image.createGraphics().apply {
                val unitWidth = width.toFloat() / elements.size
                val unitHeight = height.toFloat() / elements.size
                val unitWidthOverflow = unitWidth / 10

                for (i in 0 until elements.size) {
                    val element = elements[i]
                    val elementHeight = unitHeight * element
                    color =
                        when {
                            elements.actives.contains(i) -> activeColor
                            elements.inactives.contains(i) -> inactiveColor
                            else -> mainColor
                        }
                    fill(
                        Rectangle2D.Float(
                            unitWidth * i,
                            height - elementHeight,
                            unitWidth + unitWidthOverflow,
                            elementHeight,
                        ),
                    )
                }
            }
        }

    fun update() {
        SwingUtilities.invokeLater { updateUI() }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.drawImage(createImage(), 0, 0, null)
    }
}
