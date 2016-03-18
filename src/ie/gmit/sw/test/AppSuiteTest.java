package ie.gmit.sw.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ParseTest.class,
	ImageGeneratorTest.class,
	PositionGeneratorTest.class,
	ColourFactoryTest.class,
	ConfigurableTest.class
})

public class AppSuiteTest {

	public AppSuiteTest() {
	}

}