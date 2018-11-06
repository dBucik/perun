package cz.metacentrum.perun.webgui.json.searcher;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import cz.metacentrum.perun.webgui.client.PerunWebSession;
import cz.metacentrum.perun.webgui.json.JsonCallback;
import cz.metacentrum.perun.webgui.json.JsonCallbackEvents;
import cz.metacentrum.perun.webgui.json.JsonCallbackTable;
import cz.metacentrum.perun.webgui.json.JsonPostClient;
import cz.metacentrum.perun.webgui.json.JsonUtils;
import cz.metacentrum.perun.webgui.json.keyproviders.GeneralKeyProvider;
import cz.metacentrum.perun.webgui.model.PerunError;
import cz.metacentrum.perun.webgui.model.finder.FinderObject;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderExtSource;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderFacility;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderGroup;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderHost;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderMember;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderResource;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderService;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderUser;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderUserExtSource;
import cz.metacentrum.perun.webgui.model.finder.basic.FinderVo;
import cz.metacentrum.perun.webgui.model.finder.relations.FinderGroupResource;
import cz.metacentrum.perun.webgui.model.finder.relations.FinderMemberGroup;
import cz.metacentrum.perun.webgui.model.finder.relations.FinderMemberResource;
import cz.metacentrum.perun.webgui.model.finder.relations.FinderUserFacility;
import cz.metacentrum.perun.webgui.tabs.facilitiestabs.FacilityDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.groupstabs.GroupDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.memberstabs.MemberDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.resourcestabs.ResourceDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.servicestabs.ServiceDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.userstabs.UserDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.userstabs.UserExtSourceDetailTabItem;
import cz.metacentrum.perun.webgui.tabs.vostabs.VoDetailTabItem;
import cz.metacentrum.perun.webgui.widgets.AjaxLoaderImage;
import cz.metacentrum.perun.webgui.widgets.PerunTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("Convert2Lambda")
public class GeneralEntityFinder implements JsonCallback, JsonCallbackTable<FinderObject> {

	// session
	private PerunWebSession session = PerunWebSession.getInstance();
	// json url
	static private final String JSON_URL = "searcher/generalSearch";
	// Data provider
	private ListDataProvider<FinderObject> dataProvider = new ListDataProvider<>();
	// table
	private PerunTable<FinderObject> table;
	// table data
	private ArrayList<FinderObject> list = new ArrayList<>();
	// Selection model
	final MultiSelectionModel<FinderObject> selectionModel = new MultiSelectionModel<>(new GeneralKeyProvider<>());
	// External events
	private JsonCallbackEvents events = new JsonCallbackEvents();
	// loader image
	private AjaxLoaderImage loaderImage = new AjaxLoaderImage(true, "Enter keywords to search.");

	// sort handler
	private ListHandler<FinderObject> columnSortHandler;

	private String input;
	private boolean checkable = false;

	/**
	 * Creates a new request
	 */
	public GeneralEntityFinder() { }

	/**
	 * Creates a new request with custom events
	 * @param events
	 */
	public GeneralEntityFinder(JsonCallbackEvents events) {
		this.events = events;
	}

	/**
	 * Returns table of found entities.
	 * @return
	 */
	@Override
	public CellTable<FinderObject> getTable(){
		// retrieve data
		retrieveData();
		return getEmptyTable();

	}

	/**
	 * Returns empty table definition
	 * @return
	 */
	public CellTable<FinderObject> getEmptyTable(){
		// Table data provider.
		dataProvider = new ListDataProvider<>(list);

		// Table
		table = new PerunTable<>(list);

		// Sorting
		columnSortHandler = new ListHandler<>(dataProvider.getList());
		table.addColumnSortHandler(columnSortHandler);

		// table selection
		table.setSelectionModel(selectionModel, DefaultSelectionEventManager.createCheckboxManager());

		// set empty content & loader
		table.setEmptyTableWidget(loaderImage);

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(table);
		if (checkable) {
			table.addCheckBoxColumn();
		}

		return table;
	}

	/**
	 * Do search
	 */
	public void search(){
		loaderImage.setEmptyResultMessage("No results found.");

		clearTable();
		retrieveData();
	}

	/**
	 * Retrieves data from RPC
	 */
	@Override
	public void retrieveData() {
		loaderImage.loadingStart();

		JSONObject req = new JSONObject();
		req.put("input", new JSONString(input));

		JsonPostClient js = new JsonPostClient(new JsonCallbackEvents() {
			@Override
			public void onError(PerunError error) {
				session.getUiElements().setLogErrorText("Error while performing search.");
				loaderImage.loadingError(error);
				events.onError(error);
			}

			@Override
			public void onLoadingStart() {
				loaderImage.loadingStart();
				session.getUiElements().setLogText("Loading search results started.");
				events.onLoadingStart();
			}

			@Override
			public void onFinished(JavaScriptObject jso) {
				loaderImage.loadingFinished();
				ArrayList<FinderObject> objs = JsonUtils.jsoAsList(jso);
				if (objs != null && ! objs.isEmpty()) {
					clearTable();
					String entityType = objs.get(0).getEntityType();
					setUpTableColumns(jso, entityType, columnSortHandler);
					table.flush();
					table.redraw();
				}
				session.getUiElements().setLogText("Found results: " + list.size());
				events.onFinished(jso);
			}
		});

		js.sendData(JSON_URL, req);
	}

	/**
	 * Add object as new row to table
	 *
	 * @param object Facility to be added as new row
	 */
	@Override
	public void addToTable(FinderObject object) {
		list.add(object);
		dataProvider.flush();
		dataProvider.refresh();
	}

	/**
	 * Removes object as row from table
	 *
	 * @param object Facility to be removed as row
	 */
	@Override
	public void removeFromTable(FinderObject object) {
		list.remove(object);
		selectionModel.getSelectedSet().remove(object);
		dataProvider.flush();
		dataProvider.refresh();
	}

	/**
	 * Clear all table content
	 */
	@Override
	public void clearTable(){
		while (table.getColumnCount() > 0) {
			table.removeColumn(0);
		}
		list.clear();
		selectionModel.clear();
		dataProvider.flush();
		dataProvider.refresh();
	}

	/**
	 * Clears list of selected items
	 */
	@Override
	public void clearTableSelectedSet(){
		selectionModel.clear();
	}

	/**
	 * Return selected items from list
	 *
	 * @return return list of checked items
	 */
	@Override
	public ArrayList<FinderObject> getTableSelectedList(){
		return JsonUtils.setToList(selectionModel.getSelectedSet());
	}

	/**
	 * Called, when an error occurs
	 */
	@Override
	public void onError(PerunError error) {
		session.getUiElements().setLogErrorText("Error while loading results.");
		loaderImage.loadingError(error);
		events.onError(error);
	}

	/**
	 * Called, when loading starts
	 */
	@Override
	public void onLoadingStart() {
		session.getUiElements().setLogText("Loading results started.");
		events.onLoadingStart();
	}

	/**
	 * Called, when operation finishes successfully.
	 */
	@Override
	public void onFinished(JavaScriptObject jso) {
		loaderImage.loadingFinished();
		setList(JsonUtils.jsoAsList(jso));
		session.getUiElements().setLogText("Facilities loaded: " + list.size());
		events.onFinished(jso);
	}

	@Override
	public void insertToTable(int index, FinderObject object) {
		list.add(index, object);
		dataProvider.flush();
		dataProvider.refresh();
	}

	@Override
	public void setEditable(boolean editable) {
		// left blank intentionally
	}

	@Override
	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	@Override
	public void setList(ArrayList<FinderObject> list) {
		clearTable();
		this.list.addAll(list);
		dataProvider.flush();
		dataProvider.refresh();
	}

	@Override
	public ArrayList<FinderObject> getList() {
		return this.list;
	}

	public void setSelected(FinderObject facility) {
		selectionModel.setSelected(facility, true);
	}

	public void setEvents(JsonCallbackEvents event) {
		this.events = event;
	}

	public void clearParameters() {
		input = "";
	}

	public void setInput(String input) {
		this.input = input;
	}

	private void setUpTableColumns(JavaScriptObject obj, String entityType, ListHandler<FinderObject> columnSortHandler) {
		switch (entityType) {
			case "EXT_SOURCE":
				setUpTableForExtSource(obj, columnSortHandler);
				break;
			case "FACILITY":
				setUpTableForFacility(obj, columnSortHandler);
				break;
			case "GROUP":
				setUpTableForGroup(obj, columnSortHandler);
				break;
			case "HOST":
				setUpTableForHost(obj, columnSortHandler);
				break;
			case "MEMBER":
				setUpTableForMember(obj, columnSortHandler);
				break;
			case "RESOURCE":
				setUpTableForResource(obj, columnSortHandler);
				break;
			case "SERVICE":
				setUpTableForService(obj, columnSortHandler);
				break;
			case "USER":
				setUpTableForUser(obj, columnSortHandler);
				break;
			case "USER_EXT_SOURCE":
				setUpTableForUserExtSource(obj, columnSortHandler);
				break;
			case "VO":
				setUpTableForVo(obj, columnSortHandler);
				break;
			case "GROUP_RESOURCE":
				setUpTableForGroupResource(obj, columnSortHandler);
				break;
			case "MEMBER_GROUP":
				setUpTableForMemberGroup(obj, columnSortHandler);
				break;
			case "MEMBER_RESOURCE":
				setUpTableForMemberResource(obj, columnSortHandler);
				break;
			case "USER_FACILITY":
				setUpTableForUserFacility(obj, columnSortHandler);
				break;
		}
	}

	private void setUpTableForExtSource(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		List<FinderExtSource> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderExtSource o: data) {
			dataProvider.getList().add(o);
		}


		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderExtSource obj = (FinderExtSource) object;
				return String.valueOf(obj.getId());
			}
		}, null);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderExtSource obj1 = (FinderExtSource) o1;
				FinderExtSource obj2 = (FinderExtSource) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> nameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderExtSource obj = (FinderExtSource) object;
				return obj.getName();
			}
		}, null);

		nameColumn.setSortable(true);
		columnSortHandler.setComparator(nameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderExtSource obj1 = (FinderExtSource) o1;
				FinderExtSource obj2 = (FinderExtSource) o2;

				return obj1.getName().compareToIgnoreCase(obj2.getName());
			}
		});

		Column<FinderObject, String> typeColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderExtSource obj = (FinderExtSource) object;
				return obj.getType();
			}
		}, null);

		typeColumn.setSortable(true);
		columnSortHandler.setComparator(typeColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderExtSource obj1 = (FinderExtSource) o1;
				FinderExtSource obj2 = (FinderExtSource) o2;

				return obj1.getType().compareToIgnoreCase(obj2.getType());
			}
		});

		table.addColumn(idColumn, "ID");
		table.addColumn(nameColumn, "Name");
		table.addColumn(typeColumn, "Type");
	}

	private void setUpTableForFacility(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		//details

		FieldUpdater<FinderObject, String> facilityDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderFacility ent = (FinderFacility) dataProvider.getList().get(i);
				session.getTabManager().addTab(new FacilityDetailTabItem(ent.getId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderFacility obj = (FinderFacility) object;
				return String.valueOf(obj.getId());
			}
		}, facilityDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderFacility obj1 = (FinderFacility) o1;
				FinderFacility obj2 = (FinderFacility) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> nameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderFacility obj = (FinderFacility) object;
				return obj.getName();
			}
		}, facilityDetail);

		nameColumn.setSortable(true);
		columnSortHandler.setComparator(nameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderFacility obj1 = (FinderFacility) o1;
				FinderFacility obj2 = (FinderFacility) o2;

				return obj1.getName().compareToIgnoreCase(obj2.getName());
			}
		});

		Column<FinderObject, String> descriptionColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderFacility obj = (FinderFacility) object;
				return obj.getDescription();
			}
		}, null);

		descriptionColumn.setSortable(true);
		columnSortHandler.setComparator(descriptionColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderFacility obj1 = (FinderFacility) o1;
				FinderFacility obj2 = (FinderFacility) o2;

				return obj1.getDescription().compareToIgnoreCase(obj2.getDescription());
			}
		});

		//finish

		table.addColumn(idColumn, "ID");
		table.addColumn(nameColumn, "Name");
		table.addColumn(descriptionColumn, "Description");

		List<FinderFacility> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderFacility o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForGroup(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> groupDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderGroup ent = (FinderGroup) dataProvider.getList().get(i);
				session.getTabManager().addTab(new GroupDetailTabItem(ent.getId()));
			}
		};

		FieldUpdater<FinderObject, String> voDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderGroup ent = (FinderGroup) dataProvider.getList().get(i);
				session.getTabManager().addTab(new VoDetailTabItem(ent.getVoId()));
			}
		};

		FieldUpdater<FinderObject, String> parentGroupDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderGroup ent = (FinderGroup) dataProvider.getList().get(i);
				try {
					ent.getParentGroupId();
				} catch (NumberFormatException e) {
					// group has no parent group, return
					return;
				}
				session.getTabManager().addTab(new GroupDetailTabItem(ent.getParentGroupId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroup obj = (FinderGroup) object;
				return String.valueOf(obj.getId());
			}
		}, groupDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderGroup obj1 = (FinderGroup) o1;
				FinderGroup obj2 = (FinderGroup) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> nameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroup obj = (FinderGroup) object;
				return obj.getName();
			}
		}, groupDetail);

		nameColumn.setSortable(true);
		columnSortHandler.setComparator(nameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderGroup obj1 = (FinderGroup) o1;
				FinderGroup obj2 = (FinderGroup) o2;

				return obj1.getName().compareToIgnoreCase(obj2.getName());
			}
		});

		Column<FinderObject, String> descriptionColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroup obj = (FinderGroup) object;
				return obj.getDescription();
			}
		}, groupDetail);

		descriptionColumn.setSortable(true);
		columnSortHandler.setComparator(descriptionColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderGroup obj1 = (FinderGroup) o1;
				FinderGroup obj2 = (FinderGroup) o2;

				return obj1.getDescription().compareToIgnoreCase(obj2.getDescription());
			}
		});

		Column<FinderObject, String> voIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroup obj = (FinderGroup) object;
				return String.valueOf(obj.getVoId());
			}
		}, voDetail);

		voIdColumn.setSortable(true);
		columnSortHandler.setComparator(voIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderGroup obj1 = (FinderGroup) o1;
				FinderGroup obj2 = (FinderGroup) o2;

				return obj1.getVoId() - obj2.getVoId();
			}
		});

		Column<FinderObject, String> parentGroupIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroup obj = (FinderGroup) object;
				return String.valueOf(obj.getParentGroupId());
			}
		}, parentGroupDetail);
		parentGroupIdColumn.setSortable(false);

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(nameColumn, "Name");
		table.addColumn(descriptionColumn, "Description");
		table.addColumn(parentGroupIdColumn, "Parent group ID");
		table.addColumn(voIdColumn, "Vo ID");

		List<FinderGroup> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderGroup o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForHost(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> facilityDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderHost ent = (FinderHost) dataProvider.getList().get(i);
				session.getTabManager().addTab(new FacilityDetailTabItem(ent.getFacilityId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderHost obj = (FinderHost) object;
				return String.valueOf(obj.getId());
			}
		}, null);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderHost obj1 = (FinderHost) o1;
				FinderHost obj2 = (FinderHost) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> hostnameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderHost obj = (FinderHost) object;
				return obj.getHostname();
			}
		}, null);

		hostnameColumn.setSortable(true);
		columnSortHandler.setComparator(hostnameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderHost obj1 = (FinderHost) o1;
				FinderHost obj2 = (FinderHost) o2;

				return obj1.getHostname().compareToIgnoreCase(obj2.getHostname());
			}
		});

		Column<FinderObject, String> facilityIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderHost obj = (FinderHost) object;
				return String.valueOf(obj.getFacilityId());
			}
		}, facilityDetail);

		facilityIdColumn.setSortable(true);
		columnSortHandler.setComparator(facilityIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderHost obj1 = (FinderHost) o1;
				FinderHost obj2 = (FinderHost) o2;

				return obj1.getFacilityId() - obj2.getFacilityId();
			}
		});

		Column<FinderObject, String> descriptionColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderHost obj = (FinderHost) object;
				return obj.getDescription();
			}
		}, null);

		descriptionColumn.setSortable(true);
		columnSortHandler.setComparator(descriptionColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderHost obj1 = (FinderHost) o1;
				FinderHost obj2 = (FinderHost) o2;

				return obj1.getDescription().compareToIgnoreCase(obj2.getDescription());
			}
		});

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(hostnameColumn, "Hostname");
		table.addColumn(descriptionColumn, "Description");
		table.addColumn(facilityIdColumn, "Facility ID");

		List<FinderHost> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderHost o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForMember(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> userDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderMember ent = (FinderMember) dataProvider.getList().get(i);
				session.getTabManager().addTab(new UserDetailTabItem(ent.getUserId()));
			}
		};

		FieldUpdater<FinderObject, String> voDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderMember ent = (FinderMember) dataProvider.getList().get(i);
				session.getTabManager().addTab(new VoDetailTabItem(ent.getVoId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMember obj = (FinderMember) object;
				return String.valueOf(obj.getId());
			}
		}, null);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMember obj1 = (FinderMember) o1;
				FinderMember obj2 = (FinderMember) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> userIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMember obj = (FinderMember) object;
				return String.valueOf(obj.getUserId());
			}
		}, userDetail);

		userIdColumn.setSortable(true);
		columnSortHandler.setComparator(userIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMember obj1 = (FinderMember) o1;
				FinderMember obj2 = (FinderMember) o2;

				return obj1.getUserId() - obj2.getUserId();
			}
		});

		Column<FinderObject, String> voIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMember obj = (FinderMember) object;
				return String.valueOf(obj.getVoId());
			}
		}, voDetail);

		voIdColumn.setSortable(true);
		columnSortHandler.setComparator(voIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMember obj1 = (FinderMember) o1;
				FinderMember obj2 = (FinderMember) o2;

				return obj1.getVoId() - obj2.getVoId();
			}
		});

		Column<FinderObject, String> sponsoredColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMember obj = (FinderMember) object;
				return String.valueOf(obj.isSponsored());
			}
		}, null);

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(userIdColumn, "User ID");
		table.addColumn(voIdColumn, "Vo ID");
		table.addColumn(sponsoredColumn, "Sponsored");

		List<FinderMember> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderMember o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForResource(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> resourceDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderResource ent = (FinderResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new ResourceDetailTabItem(ent.getId(), ent.getFacilityId()));
			}
		};

		FieldUpdater<FinderObject, String> facilityDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderResource ent = (FinderResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new FacilityDetailTabItem(ent.getFacilityId()));
			}
		};

		FieldUpdater<FinderObject, String> voDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderResource ent = (FinderResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new VoDetailTabItem(ent.getVoId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderResource obj = (FinderResource) object;
				return String.valueOf(obj.getId());
			}
		}, resourceDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderResource obj1 = (FinderResource) o1;
				FinderResource obj2 = (FinderResource) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> facilityIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderResource obj = (FinderResource) object;
				return String.valueOf(obj.getFacilityId());
			}
		}, facilityDetail);

		facilityIdColumn.setSortable(true);
		columnSortHandler.setComparator(facilityIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderResource obj1 = (FinderResource) o1;
				FinderResource obj2 = (FinderResource) o2;

				return obj1.getFacilityId() - obj2.getFacilityId();
			}
		});

		Column<FinderObject, String> nameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderResource obj = (FinderResource) object;
				return obj.getName();
			}
		}, resourceDetail);

		nameColumn.setSortable(true);
		columnSortHandler.setComparator(nameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderResource obj1 = (FinderResource) o1;
				FinderResource obj2 = (FinderResource) o2;

				return obj1.getName().compareToIgnoreCase(obj2.getName());
			}
		});

		Column<FinderObject, String> descriptionColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderResource obj = (FinderResource) object;
				return obj.getDescription();
			}
		}, resourceDetail);

		descriptionColumn.setSortable(true);
		columnSortHandler.setComparator(descriptionColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderResource obj1 = (FinderResource) o1;
				FinderResource obj2 = (FinderResource) o2;

				return obj1.getDescription().compareToIgnoreCase(obj2.getDescription());
			}
		});

		Column<FinderObject, String> voIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderResource obj = (FinderResource) object;
				return String.valueOf(obj.getVoId());
			}
		}, voDetail);

		voIdColumn.setSortable(true);
		columnSortHandler.setComparator(voIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderResource obj1 = (FinderResource) o1;
				FinderResource obj2 = (FinderResource) o2;

				return obj1.getVoId() - obj2.getVoId();
			}
		});

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(nameColumn, "Name");
		table.addColumn(descriptionColumn, "Description");
		table.addColumn(facilityIdColumn, "Facility ID");
		table.addColumn(voIdColumn, "Vo ID");

		List<FinderResource> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderResource o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForService(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> serviceDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderService ent = (FinderService) dataProvider.getList().get(i);
				session.getTabManager().addTab(new ServiceDetailTabItem(ent.getId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderService obj = (FinderService) object;
				return String.valueOf(obj.getId());
			}
		}, serviceDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderService obj1 = (FinderService) o1;
				FinderService obj2 = (FinderService) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> nameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderService obj = (FinderService) object;
				return obj.getName();
			}
		}, serviceDetail);

		nameColumn.setSortable(true);
		columnSortHandler.setComparator(nameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderService obj1 = (FinderService) o1;
				FinderService obj2 = (FinderService) o2;

				return obj1.getName().compareToIgnoreCase(obj2.getName());
			}
		});

		Column<FinderObject, String> descriptionColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderService obj = (FinderService) object;
				return obj.getDescription();
			}
		}, serviceDetail);

		descriptionColumn.setSortable(true);
		columnSortHandler.setComparator(descriptionColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderService obj1 = (FinderService) o1;
				FinderService obj2 = (FinderService) o2;

				return obj1.getDescription().compareToIgnoreCase(obj2.getDescription());
			}
		});

		Column<FinderObject, String> delayColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderService obj = (FinderService) object;
				return String.valueOf(obj.getDelay());
			}
		}, serviceDetail);

		delayColumn.setSortable(true);
		columnSortHandler.setComparator(delayColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderService obj1 = (FinderService) o1;
				FinderService obj2 = (FinderService) o2;

				return obj1.getDelay() - obj2.getDelay();
			}
		});

		Column<FinderObject, String> recurrenceColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderService obj = (FinderService) object;
				return String.valueOf(obj.getRecurrence());
			}
		}, serviceDetail);

		recurrenceColumn.setSortable(true);
		columnSortHandler.setComparator(recurrenceColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderService obj1 = (FinderService) o1;
				FinderService obj2 = (FinderService) o2;

				return obj1.getRecurrence() - obj2.getRecurrence();
			}
		});

		Column<FinderObject, String> scriptColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderService obj = (FinderService) object;
				return obj.getScript();
			}
		}, serviceDetail);
		scriptColumn.setSortable(false);

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(nameColumn, "Name");
		table.addColumn(descriptionColumn, "Description");
		table.addColumn(delayColumn, "Delay");
		table.addColumn(recurrenceColumn, "Recurrence");
		table.addColumn(scriptColumn, "Script");

		List<FinderService> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderService o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForUser(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> userDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderUser ent = (FinderUser) dataProvider.getList().get(i);
				session.getTabManager().addTab(new UserDetailTabItem(ent.getId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return String.valueOf(obj.getId());
			}
		}, userDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> titleBeforeColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return obj.getTitleBefore();
			}
		}, userDetail);

		titleBeforeColumn.setSortable(true);
		columnSortHandler.setComparator(titleBeforeColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return obj1.getTitleBefore().compareToIgnoreCase(obj2.getTitleBefore());
			}
		});

		Column<FinderObject, String> firstNameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return obj.getFirstName();
			}
		}, userDetail);

		firstNameColumn.setSortable(true);
		columnSortHandler.setComparator(firstNameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return obj1.getFirstName().compareToIgnoreCase(obj2.getFirstName());
			}
		});

		Column<FinderObject, String> middleNameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return obj.getMiddleName();
			}
		}, userDetail);

		middleNameColumn.setSortable(true);
		columnSortHandler.setComparator(middleNameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return obj1.getMiddleName().compareToIgnoreCase(obj2.getMiddleName());
			}
		});

		Column<FinderObject, String> lastNameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return obj.getLastName();
			}
		}, userDetail);

		lastNameColumn.setSortable(true);
		columnSortHandler.setComparator(lastNameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return obj1.getLastName().compareToIgnoreCase(obj2.getLastName());
			}
		});

		Column<FinderObject, String> titleAfterColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return obj.getTitleAfter();
			}
		}, userDetail);

		titleAfterColumn.setSortable(true);
		columnSortHandler.setComparator(titleAfterColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return obj1.getTitleAfter().compareToIgnoreCase(obj2.getTitleAfter());
			}
		});

		Column<FinderObject, String> serviceAccColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return String.valueOf(obj.isServiceAcc());
			}
		}, userDetail);

		serviceAccColumn.setSortable(true);
		columnSortHandler.setComparator(serviceAccColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return Boolean.compare(obj1.isServiceAcc(), obj2.isServiceAcc());
			}
		});

		Column<FinderObject, String> sponsoredAccColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUser obj = (FinderUser) object;
				return String.valueOf(obj.isSponsoredAcc());
			}
		}, userDetail);

		sponsoredAccColumn.setSortable(true);
		columnSortHandler.setComparator(sponsoredAccColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUser obj1 = (FinderUser) o1;
				FinderUser obj2 = (FinderUser) o2;

				return Boolean.compare(obj1.isSponsoredAcc(), obj2.isSponsoredAcc());
			}
		});

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(titleBeforeColumn, "Title before");
		table.addColumn(firstNameColumn, "First name");
		table.addColumn(middleNameColumn, "Middle name");
		table.addColumn(lastNameColumn, "Last name");
		table.addColumn(titleAfterColumn, "Title after");
		table.addColumn(serviceAccColumn, "Service account");
		table.addColumn(sponsoredAccColumn, "Sponsored account");

		List<FinderUser> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderUser o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForUserExtSource(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> uesDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderUserExtSource ent = (FinderUserExtSource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new UserExtSourceDetailTabItem(ent.getId()));
			}
		};

		FieldUpdater<FinderObject, String> userDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderUserExtSource ent = (FinderUserExtSource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new UserDetailTabItem(ent.getUserId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserExtSource obj = (FinderUserExtSource) object;
				return String.valueOf(obj.getId());
			}
		}, uesDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserExtSource obj1 = (FinderUserExtSource) o1;
				FinderUserExtSource obj2 = (FinderUserExtSource) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> loginExtColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserExtSource obj = (FinderUserExtSource) object;
				return obj.getLoginExt();
			}
		}, uesDetail);

		loginExtColumn.setSortable(true);
		columnSortHandler.setComparator(loginExtColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserExtSource obj1 = (FinderUserExtSource) o1;
				FinderUserExtSource obj2 = (FinderUserExtSource) o2;

				return obj1.getLoginExt().compareToIgnoreCase(obj2.getLoginExt());
			}
		});

		Column<FinderObject, String> extSourceIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserExtSource obj = (FinderUserExtSource) object;
				return String.valueOf(obj.getExtSourceId());
			}
		}, null);

		extSourceIdColumn.setSortable(true);
		columnSortHandler.setComparator(extSourceIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserExtSource obj1 = (FinderUserExtSource) o1;
				FinderUserExtSource obj2 = (FinderUserExtSource) o2;

				return obj1.getExtSourceId() - obj2.getExtSourceId();
			}
		});

		Column<FinderObject, String> userIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserExtSource obj = (FinderUserExtSource) object;
				return String.valueOf(obj.getUserId());
			}
		}, userDetail);

		userIdColumn.setSortable(true);
		columnSortHandler.setComparator(userIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserExtSource obj1 = (FinderUserExtSource) o1;
				FinderUserExtSource obj2 = (FinderUserExtSource) o2;

				return obj1.getUserId() - obj2.getUserId();
			}
		});

		Column<FinderObject, String> loaColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserExtSource obj = (FinderUserExtSource) object;
				return String.valueOf(obj.getLoa());
			}
		}, uesDetail);

		loaColumn.setSortable(true);
		columnSortHandler.setComparator(loaColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserExtSource obj1 = (FinderUserExtSource) o1;
				FinderUserExtSource obj2 = (FinderUserExtSource) o2;

				return obj1.getLoa() - obj2.getLoa();
			}
		});

		Column<FinderObject, String> lastAccessColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserExtSource obj = (FinderUserExtSource) object;
				return String.valueOf(obj.getLastAccess());
			}
		}, uesDetail);

		lastAccessColumn.setSortable(true);
		columnSortHandler.setComparator(lastAccessColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserExtSource obj1 = (FinderUserExtSource) o1;
				FinderUserExtSource obj2 = (FinderUserExtSource) o2;

				return obj1.getLastAccess() - obj2.getLastAccess();
			}
		});

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(loginExtColumn, "Login ext");
		table.addColumn(extSourceIdColumn, "Ext Source ID");
		table.addColumn(userIdColumn, "User ID");
		table.addColumn(loaColumn, "Loa");
		table.addColumn(lastAccessColumn, "Last access");

		List<FinderUserExtSource> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderUserExtSource o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForVo(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> voDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderVo ent = (FinderVo) dataProvider.getList().get(i);
				session.getTabManager().addTab(new VoDetailTabItem(ent.getId()));
			}
		};

		// columns

		Column<FinderObject, String> idColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderVo obj = (FinderVo) object;
				return String.valueOf(obj.getId());
			}
		}, voDetail);

		idColumn.setSortable(true);
		columnSortHandler.setComparator(idColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderVo obj1 = (FinderVo) o1;
				FinderVo obj2 = (FinderVo) o2;

				return obj1.getId() - obj2.getId();
			}
		});

		Column<FinderObject, String> shortNameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderVo obj = (FinderVo) object;
				return obj.getShortName();
			}
		}, voDetail);

		shortNameColumn.setSortable(true);
		columnSortHandler.setComparator(shortNameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderVo obj1 = (FinderVo) o1;
				FinderVo obj2 = (FinderVo) o2;

				return obj1.getShortName().compareToIgnoreCase(obj2.getShortName());
			}
		});

		Column<FinderObject, String> nameColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderVo obj = (FinderVo) object;
				return obj.getName();
			}
		}, voDetail);

		nameColumn.setSortable(true);
		columnSortHandler.setComparator(nameColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderVo obj1 = (FinderVo) o1;
				FinderVo obj2 = (FinderVo) o2;

				return obj1.getName().compareToIgnoreCase(obj2.getName());
			}
		});

		// finish

		table.addColumn(idColumn, "ID");
		table.addColumn(shortNameColumn, "Short name");
		table.addColumn(nameColumn, "Name");

		List<FinderVo> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderVo o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForGroupResource(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> groupDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderGroupResource ent = (FinderGroupResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new GroupDetailTabItem(ent.getGroupId()));
			}
		};

		FieldUpdater<FinderObject, String> resourceDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderGroupResource ent = (FinderGroupResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new ResourceDetailTabItem(ent.getResourceId(), 0));
			}
		};

		// columns

		Column<FinderObject, String> groupIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroupResource obj = (FinderGroupResource) object;
				return String.valueOf(obj.getGroupId());
			}
		}, groupDetail);

		groupIdColumn.setSortable(true);
		columnSortHandler.setComparator(groupIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderGroupResource obj1 = (FinderGroupResource) o1;
				FinderGroupResource obj2 = (FinderGroupResource) o2;

				return obj1.getGroupId() - obj2.getGroupId();
			}
		});

		Column<FinderObject, String> resourceIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderGroupResource obj = (FinderGroupResource) object;
				return String.valueOf(obj.getResourceId());
			}
		}, resourceDetail);

		resourceIdColumn.setSortable(true);
		columnSortHandler.setComparator(resourceIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderGroupResource obj1 = (FinderGroupResource) o1;
				FinderGroupResource obj2 = (FinderGroupResource) o2;

				return obj1.getResourceId() - obj2.getResourceId();
			}
		});

		// finish

		table.addColumn(groupIdColumn, "Group ID");
		table.addColumn(resourceIdColumn, "Resource ID");

		List<FinderGroupResource> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderGroupResource o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForMemberGroup(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> memberDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderMemberGroup ent = (FinderMemberGroup) dataProvider.getList().get(i);
				session.getTabManager().addTab(new MemberDetailTabItem(ent.getMemberId(), ent.getGroupId()));
			}
		};

		FieldUpdater<FinderObject, String> groupDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderMemberGroup ent = (FinderMemberGroup) dataProvider.getList().get(i);
				session.getTabManager().addTab(new GroupDetailTabItem(ent.getGroupId()));
			}
		};

		// columns

		Column<FinderObject, String> memberIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMemberGroup obj = (FinderMemberGroup) object;
				return String.valueOf(obj.getMemberId());
			}
		}, memberDetail);

		memberIdColumn.setSortable(true);
		columnSortHandler.setComparator(memberIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMemberGroup obj1 = (FinderMemberGroup) o1;
				FinderMemberGroup obj2 = (FinderMemberGroup) o2;

				return obj1.getMemberId() - obj2.getMemberId();
			}
		});

		Column<FinderObject, String> groupIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMemberGroup obj = (FinderMemberGroup) object;
				return String.valueOf(obj.getGroupId());
			}
		}, groupDetail);

		groupIdColumn.setSortable(true);
		columnSortHandler.setComparator(groupIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMemberGroup obj1 = (FinderMemberGroup) o1;
				FinderMemberGroup obj2 = (FinderMemberGroup) o2;

				return obj1.getGroupId() - obj2.getGroupId();
			}
		});

		// finish

		table.addColumn(memberIdColumn, "Member ID");
		table.addColumn(groupIdColumn, "Group ID");

		List<FinderMemberGroup> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderMemberGroup o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForMemberResource(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		// details

		FieldUpdater<FinderObject, String> memberDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderMemberResource ent = (FinderMemberResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new MemberDetailTabItem(ent.getMemberId()));
			}
		};

		FieldUpdater<FinderObject, String> resourceDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderMemberResource ent = (FinderMemberResource) dataProvider.getList().get(i);
				session.getTabManager().addTab(new ResourceDetailTabItem(ent.getResourceId(), 0));
			}
		};

		// columns

		Column<FinderObject, String> memberIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMemberResource obj = (FinderMemberResource) object;
				return String.valueOf(obj.getMemberId());
			}
		}, memberDetail);

		memberIdColumn.setSortable(true);
		columnSortHandler.setComparator(memberIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMemberResource obj1 = (FinderMemberResource) o1;
				FinderMemberResource obj2 = (FinderMemberResource) o2;

				return obj1.getMemberId() - obj2.getMemberId();
			}
		});

		Column<FinderObject, String> resourceIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderMemberResource obj = (FinderMemberResource) object;
				return String.valueOf(obj.getResourceId());
			}
		}, resourceDetail);

		resourceIdColumn.setSortable(true);
		columnSortHandler.setComparator(resourceIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderMemberResource obj1 = (FinderMemberResource) o1;
				FinderMemberResource obj2 = (FinderMemberResource) o2;

				return obj1.getResourceId() - obj2.getResourceId();
			}
		});

		// finish

		table.addColumn(memberIdColumn, "Member ID");
		table.addColumn(resourceIdColumn, "Resource ID");

		List<FinderMemberResource> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderMemberResource o: data) {
			dataProvider.getList().add(o);
		}
	}

	private void setUpTableForUserFacility(JavaScriptObject jso, ListHandler<FinderObject> columnSortHandler) {
		//details
		FieldUpdater<FinderObject, String> userDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderUserFacility ent = (FinderUserFacility) dataProvider.getList().get(i);
				session.getTabManager().addTab(new UserDetailTabItem(ent.getUserId()));
			}
		};

		FieldUpdater<FinderObject, String> facilityDetail = new FieldUpdater<FinderObject, String>() {
			@Override
			public void update(int i, FinderObject object, String s) {
				FinderUserFacility ent = (FinderUserFacility) dataProvider.getList().get(i);
				session.getTabManager().addTab(new FacilityDetailTabItem(ent.getFacilityId()));
			}
		};

		//columns
		Column<FinderObject, String> userIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserFacility obj = (FinderUserFacility) object;
				return String.valueOf(obj.getUserId());
			}
		}, userDetail);

		userIdColumn.setSortable(true);
		columnSortHandler.setComparator(userIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserFacility obj1 = (FinderUserFacility) o1;
				FinderUserFacility obj2 = (FinderUserFacility) o2;

				return obj1.getUserId() - obj2.getUserId();
			}
		});

		Column<FinderObject, String> facilityIdColumn = JsonUtils.addColumn(new JsonUtils.GetValue<FinderObject, String>() {
			@Override
			public String getValue(FinderObject object) {
				FinderUserFacility obj = (FinderUserFacility) object;
				return String.valueOf(obj.getFacilityId());
			}
		}, facilityDetail);

		facilityIdColumn.setSortable(true);
		columnSortHandler.setComparator(facilityIdColumn, new Comparator<FinderObject>() {
			@Override
			public int compare(FinderObject o1, FinderObject o2) {
				FinderUserFacility obj1 = (FinderUserFacility) o1;
				FinderUserFacility obj2 = (FinderUserFacility) o2;

				return obj1.getFacilityId() - obj2.getFacilityId();
			}
		});

		// finish
		table.addColumn(userIdColumn, "User ID");
		table.addColumn(facilityIdColumn, "Facility ID");

		List<FinderUserFacility> data = JsonUtils.jsoAsList(jso);
		dataProvider.getList().clear();
		for (FinderUserFacility o: data) {
			dataProvider.getList().add(o);
		}
	}
}
