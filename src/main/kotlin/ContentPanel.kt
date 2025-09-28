import net.miginfocom.swing.MigLayout
import java.util.concurrent.TimeUnit
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JSlider
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

class ContentPanel : JPanel(MigLayout()) {
    private val elements = Elements()

    private val delaySpinner = JSpinner(SpinnerNumberModel(1, 1, 1000, 1))
    private val timeBox = JComboBox(Time.entries.toTypedArray())
    private val startButton = JButton("Start")

    private val shuffleButton = JButton("Shuffle")
    private val elementSlider = JSlider(10, 1000)
    private val stepButton = JButton("Step")

    private val graphicsPanel = GraphicsPanel(elements)

    init {
        timeBox.selectedItem = Time.MILLI
        startButton.addActionListener {
            Thread.startVirtualThread {
                delaySpinner.isEnabled = false
                timeBox.isEnabled = false
                startButton.isEnabled = false
                shuffleButton.isEnabled = false
                elementSlider.isEnabled = false
                stepButton.isEnabled = false
                while (withUpdate { elements.step() }) {
                    val unit = (timeBox.selectedItem as Time).unit
                    unit.sleep((delaySpinner.value as Int).toLong())
                }
                delaySpinner.isEnabled = true
                timeBox.isEnabled = true
                shuffleButton.isEnabled = true
                elementSlider.isEnabled = true
                Thread.currentThread().interrupt()
            }
        }

        shuffleButton.addActionListener {
            startButton.isEnabled = true
            stepButton.isEnabled = true
            withUpdate { elements.shuffle() }
        }
        elementSlider.apply {
            addChangeListener {
                startButton.isEnabled = true
                stepButton.isEnabled = true
                withUpdate { elements.size = value }
            }
            majorTickSpacing = (maximum - minimum) / 3
            paintTicks = true
            paintLabels = true
            value = maximum / 10
        }
        stepButton.addActionListener { stepButton.isEnabled = withUpdate { elements.step() } }

        add(delaySpinner, "center, split")
        add(timeBox)
        add(startButton, "wrap")
        add(shuffleButton, "center, split")
        add(elementSlider)
        add(stepButton, "wrap")
        add(graphicsPanel, "push, grow")
    }

    private fun <R> withUpdate(block: () -> R): R {
        val value = block()
        graphicsPanel.update()
        return value
    }

    enum class Time(
        val unit: TimeUnit,
    ) {
        MICRO(TimeUnit.MICROSECONDS),
        MILLI(TimeUnit.MILLISECONDS),
        SECONDS(TimeUnit.SECONDS),
        ;

        override fun toString(): String = unit.name.lowercase().replaceFirstChar { it.titlecaseChar() }
    }
}
