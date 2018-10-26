package cz.metacentrum.perun.finder.service;

import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.MemberInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.UserInput;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonToInputParserParsingUserInputTest {

	private String input;
	private List<InputAttribute> core;
	private List<InputAttribute> attributes;
	private List<String> attributeNames;
	private List<InputEntity> innerInputs;


	@Before
	public void setUp() throws Exception {
		setUpInputString();
		setUpCoreAttributes();
		setUpAttributes();
		setUpAttributesNames();
		setUpInnerInputs();
	}

	private void setUpInputString() {
		this.input = "{\n" +
				"  \"entityName\": \"USER\",\n" +
				"  \"id\": {\"value\": [1]},\n" +
				"  \"firstName\": {\"value\": [\"firstName1\"]},\n" +
				"  \"middleName\": {\"value\": [\"middleName1\"]},\n" +
				"  \"lastName\": {\"value\": [\"lastName1\"]},\n" +
				"  \"titleBefore\": {\"value\": [\"titleBefore1\"]},\n" +
				"  \"titleAfter\": {\"value\": [\"titleAfter1\"]},\n" +
				"  \"sponsoredAcc\": {\"value\": [true]},\n" +
				"  \"serviceAcc\": {\"value\": [true]},\n" +
				"  \"attributes\": [\n" +
				"    {\n" +
				"      \"name\": \"attribute1\",\n" +
				"      \"value\": [1]\n" +
				"    }\n" +
				"  ],\n" +
				"  \"attributeNames\": [\n" +
				"    \"attribute2\"\n" +
				"  ],\n" +
				"  \"relations\": [\n" +
				"    {\n" +
				"      \"entityName\": \"MEMBER\"" +
				"    }\n" +
				"  ]\n" +
				"}";
	}

	private void setUpCoreAttributes() throws AttributeTypeException {
		List<InputAttribute> core = new ArrayList<>();
		JSONArray val1 = new JSONArray();
		val1.put(1);
		JSONArray val2 = new JSONArray();
		val2.put("firstName1");
		JSONArray val3 = new JSONArray();
		val3.put("middleName1");
		JSONArray val4 = new JSONArray();
		val4.put("lastName1");
		JSONArray val5 = new JSONArray();
		val5.put("titleBefore1");
		JSONArray val6 = new JSONArray();
		val6.put("titleAfter1");
		JSONArray val7 = new JSONArray();
		val7.put("1");
		JSONArray val8 = new JSONArray();
		val8.put("1");
		core.add(new InputAttribute("id", false, val1));
		core.add(new InputAttribute("first_name", false, val2));
		core.add(new InputAttribute("middle_name", false, val3));
		core.add(new InputAttribute("last_name", false, val4));
		core.add(new InputAttribute("title_before", false, val5));
		core.add(new InputAttribute("title_after", false, val6));
		core.add(new InputAttribute("sponsored_acc", false, val7));
		core.add(new InputAttribute("service_acc", false, val8));
		this.core = core;
	}

	private void setUpAttributes() throws Exception {
		List<InputAttribute> attributes = new ArrayList<>();
		JSONArray value = new JSONArray();
		value.put(1);
		InputAttribute attr = new InputAttribute("attribute1", false, value);
		attributes.add(attr);

		this.attributes = attributes;
	}

	private void setUpAttributesNames() {
		List<String> attributeNames = new ArrayList<>();
		attributeNames.add("attribute2");

		this.attributeNames = attributeNames;
	}

	private void setUpInnerInputs() throws Exception {
		List<InputAttribute> core = new ArrayList<>();
		List<InputAttribute> attributes = new ArrayList<>();
		List<String> attributeNames = new ArrayList<>();
		List<InputEntity> inputs = new ArrayList<>();

		MemberInput member = new MemberInput(false, core, attributes, attributeNames, inputs);

		List<InputEntity> innerInputs = new ArrayList<>();
		innerInputs.add(member);

		this.innerInputs = innerInputs;
	}

	@Test
	public void testUserInputClass() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(entity instanceof UserInput);
	}

	@Test
	public void testUserInputCore() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.core, entity.getCore()));
	}

	@Test
	public void testUserInputAttributes() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.attributes, entity.getAttributes()));
	}

	@Test
	public void testUserInputAttributeNames() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.attributeNames, entity.getAttrNames()));
	}

	@Test
	public void testUserInputInnerInputs() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.innerInputs, entity.getInnerInputs()));
	}
}