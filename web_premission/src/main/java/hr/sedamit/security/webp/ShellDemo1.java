package hr.sedamit.security.webp;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

//@ShellComponent
public class ShellDemo1 {

//	@ShellMethod("Add two integers together.")
	public int add(int a, int b) {
		return a + b;
	}
}
