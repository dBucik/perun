package cz.metacentrum.perun.finder.service;

import cz.metacentrum.perun.finder.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perun.finder.persistence.models.InputAttribute;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.GroupInput;
import cz.metacentrum.perun.finder.persistence.models.inputEntities.basic.ResourceInput;
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
public class JsonToInputParserParsingGroupInputTest {

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
				"  \"entityName\": \"GROUP\",\n" +
				"  \"id\": {\"value\": [1]},\n" +
				"  \"name\": {\"value\": [\"group1\"]},\n" +
				"  \"description\": {\"value\": [\"description1\"]},\n" +
				"  \"voId\": {\"value\": [1]},\n" +
				"  \"parentGroupId\": {\"value\": [2]},\n" +
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
				"      \"entityName\": \"RESOURCE\"" +
				"    }\n" +
				"  ]\n" +
				"}";
	}

	private void setUpCoreAttributes() throws AttributeTypeException {
		List<InputAttribute> core = new ArrayList<>();
		JSONArray val1 = new JSONArray();
		val1.put(1);
		JSONArray val2 = new JSONArray();
		val2.put("group1");
		JSONArray val3 = new JSONArray();
		val3.put("description1");
		JSONArray val4 = new JSONArray();
		val4.put(1);
		JSONArray val5 = new JSONArray();
		val5.put(2);
		core.add(new InputAttribute("id", false, val1));
		core.add(new InputAttribute("name", false, val2));
		core.add(new InputAttribute("dsc", false, val3));
		core.add(new InputAttribute("vo_id", false, val4));
		core.add(new InputAttribute("parent_group_id", false, val5));
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

		ResourceInput group = new ResourceInput(false, core, attributes, attributeNames, inputs);

		List<InputEntity> innerInputs = new ArrayList<>();
		innerInputs.add(group);

		this.innerInputs = innerInputs;
	}

	@Test
	public void testGroupInputClass() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(entity instanceof GroupInput);
	}

	@Test
	public void testGroupInputCore() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.core, entity.getCore()));
	}

	@Test
	public void testGroupInputAttributes() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.attributes, entity.getAttributes()));
	}

	@Test
	public void testGroupInputAttributeNames() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.attributeNames, entity.getAttrNames()));
	}

	@Test
	public void testGroupInputInnerInputs() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(CollectionUtils.isEqualCollection(this.innerInputs, entity.getInnerInputs()));
	}

}