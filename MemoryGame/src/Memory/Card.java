package Memory;
import javax.swing.JButton;
//Card class inherits from JButton
public class Card extends JButton {

	//For determining what image to give it when its clicked
	private String iconName = null;
	private boolean matched = false;
	//IconName getters and setters
	public void setIconName(String s) {
		iconName = s;
	}
	public String getIconName() {
		return iconName;
	}
	//Matched getters and setters
	public void setMatched(boolean s) {
		matched = s;
	}
	public boolean getMatched() {
		return matched;
	}
}
