package org.ballproject.knime.base.wrapper;

import static org.junit.Assert.assertEquals;

import org.ballproject.knime.test.data.TestDataSource;
import org.junit.Test;

import com.genericworkflownodes.knime.cliwrapper.CLIElement;
import com.genericworkflownodes.knime.config.CTDNodeConfigurationReader;
import com.genericworkflownodes.knime.config.INodeConfiguration;

public class GenericToolWrapperTest {

	@Test
	public void testCTDLoading() throws Exception {
		CTDNodeConfigurationReader reader = new CTDNodeConfigurationReader();
		INodeConfiguration config = reader.read(TestDataSource.class
				.getResourceAsStream("test5.ctd"));

		assertEquals(2, config.getCLI().getCLIElement().size());

		CLIElement firstCLIElement = config.getCLI().getCLIElement().get(0);

		assertEquals("", firstCLIElement.getName());
		assertEquals("-i", firstCLIElement.getOptionIdentifier());
		assertEquals(false, firstCLIElement.isList());
		assertEquals(false, firstCLIElement.isRequired());

		assertEquals(1, firstCLIElement.getMapping().size());
		assertEquals("blastall.i", firstCLIElement.getMapping().get(0)
				.getRefName());

		CLIElement secondCLIElement = config.getCLI().getCLIElement().get(1);
		assertEquals("", secondCLIElement.getName());
		assertEquals("-d", secondCLIElement.getOptionIdentifier());
		assertEquals(false, secondCLIElement.isList());
		assertEquals(false, secondCLIElement.isRequired());

		assertEquals(1, secondCLIElement.getMapping().size());
		assertEquals("blastall.d", secondCLIElement.getMapping().get(0)
				.getRefName());
	}
}