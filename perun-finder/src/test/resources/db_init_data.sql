INSERT INTO ext_sources(id, name, type) VALUES (1, 'ext_source1', 'type1');
INSERT INTO ext_sources(id, name, type) VALUES (2, 'ext_source2', 'type2');
INSERT INTO ext_sources(id, name, type) VALUES (23, 'ext_source23', 'type23');

INSERT INTO facilities(id, name, dsc) VALUES (1, 'facility1', 'dsc1');
INSERT INTO facilities(id, name, dsc) VALUES (2, 'facility2', 'dsc2');
INSERT INTO facilities(id, name, dsc) VALUES (23, 'facility23', 'dsc23');

INSERT INTO vos(id, name, short_name) VALUES (1, 'virtual_organization1', 'vo1');
INSERT INTO vos(id, name, short_name) VALUES (2, 'virtual_organization2', 'vo2');
INSERT INTO vos(id, name, short_name) VALUES (23, 'virtual_organization23', 'vo23');
INSERT INTO services(id, name, description, delay, recurrence, enabled, script)
VALUES (1, 'service1', 'dsc1', 1, 1, '1', 'script1');
INSERT INTO services(id, name, description, delay, recurrence, enabled, script)
VALUES (2, 'service2', 'dsc2', 2, 2, '0', 'script2');
INSERT INTO services(id, name, description, delay, recurrence, enabled, script)
VALUES (23, 'service23', 'dsc23', 23, 23, '0', 'script23');

INSERT INTO users(id, first_name, last_name, middle_name, title_before, title_after, service_acc, sponsored_acc)
VALUES (1, 'first_name1', 'last_name1', 'middle_name1', 'title_before1', 'title_after1', '1', '1' );
INSERT INTO users(id, first_name, last_name, middle_name, title_before, title_after, service_acc, sponsored_acc)
VALUES (2, 'first_name2', 'last_name2', 'middle_name2', 'title_before2', 'title_after2', '0', '0' );
INSERT INTO users(id, first_name, last_name, middle_name, title_before, title_after, service_acc, sponsored_acc)
VALUES (23, 'first_name23', 'last_name23', 'middle_name23', 'title_before23', 'title_after23', '0', '0' );

INSERT INTO hosts(id, hostname, facility_id, dsc) VALUES (1, 'hostname1', 1, 'dsc1');
INSERT INTO hosts(id, hostname, facility_id, dsc) VALUES (2, 'hostname2', 2, 'dsc2');
INSERT INTO hosts(id, hostname, facility_id, dsc) VALUES (23, 'hostname23', 23, 'dsc23');

INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (1, 'group1', 'dsc1', 1, null);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (2, 'group2', 'dsc2', 2, null);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (23, 'group23', 'dsc23', 2, 2);
INSERT INTO groups(id, name, dsc, vo_id, parent_group_id) VALUES (123, 'group123', 'dsc123', 23, 23);

INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (1, 1, 'login_ext1', 1, 0, (TIMESTAMP '2018-01-01 08:00:00'));
INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (2, 2, 'login_ext2', 2, 1, (TIMESTAMP '2018-01-02 08:00:00'));
INSERT INTO user_ext_sources(id, user_id, login_ext, ext_sources_id, loa, last_access)
VALUES (23, 23, 'login_ext23', 23, 1, (TIMESTAMP '2018-01-02 08:00:00'));

INSERT INTO members(id, vo_id, user_id, sponsored) VALUES (1, 1, 1, true);
INSERT INTO members(id, vo_id, user_id, sponsored) VALUES (2, 2, 2, false);
INSERT INTO members(id, vo_id, user_id, sponsored) VALUES (23, 23, 23, false);

INSERT INTO resources(id, facility_id, name, dsc, vo_id) VALUES (1, 1, 'resource1', 'dsc1', 1);
INSERT INTO resources(id, facility_id, name, dsc, vo_id) VALUES (2, 2, 'resource2', 'dsc2', 2);
INSERT INTO resources(id, facility_id, name, dsc, vo_id) VALUES (23, 23, 'resource23', 'dsc23', 23);

INSERT INTO attr_names(id, attr_name, type) VALUES (1, 'facility_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (2, 'facility_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (3, 'facility_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (4, 'facility_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (5, 'facility_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (6, 'facility_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (7, 'facility_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (8, 'group_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (9, 'group_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (10, 'group_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (11, 'group_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (12, 'group_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (13, 'group_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (14, 'group_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (15, 'host_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (16, 'host_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (17, 'host_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (18, 'host_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (19, 'host_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (20, 'host_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (21, 'host_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (22, 'member_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (23, 'member_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (24, 'member_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (25, 'member_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (26, 'member_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (27, 'member_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (28, 'member_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (29, 'resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (30, 'resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (31, 'resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (32, 'resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (33, 'resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (34, 'resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (35, 'resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (36, 'user_ext_source_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (37, 'user_ext_source_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (38, 'user_ext_source_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (39, 'user_ext_source_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (40, 'user_ext_source_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (41, 'user_ext_source_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (42, 'user_ext_source_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (43, 'user_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (44, 'user_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (45, 'user_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (46, 'user_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (47, 'user_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (48, 'user_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (49, 'user_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (50, 'vo_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (51, 'vo_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (52, 'vo_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (53, 'vo_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (54, 'vo_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (55, 'vo_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (56, 'vo_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (57, 'group_resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (58, 'group_resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (59, 'group_resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (60, 'group_resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (61, 'group_resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (62, 'group_resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (63, 'group_resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (64, 'member_group_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (65, 'member_group_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (66, 'member_group_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (67, 'member_group_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (68, 'member_group_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (69, 'member_group_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (70, 'member_group_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (71, 'member_resource_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (72, 'member_resource_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (73, 'member_resource_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (74, 'member_resource_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (75, 'member_resource_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (76, 'member_resource_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (77, 'member_resource_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (78, 'user_facility_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (79, 'user_facility_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (80, 'user_facility_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (81, 'user_facility_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (82, 'user_facility_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (83, 'user_facility_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (84, 'user_facility_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO attr_names(id, attr_name, type) VALUES (85, 'service_attr_str', 'java.lang.String');
INSERT INTO attr_names(id, attr_name, type) VALUES (86, 'service_attr_int', 'java.lang.Integer');
INSERT INTO attr_names(id, attr_name, type) VALUES (87, 'service_attr_bool', 'java.lang.Boolean');
INSERT INTO attr_names(id, attr_name, type) VALUES (88, 'service_attr_array', 'java.util.ArrayList');
INSERT INTO attr_names(id, attr_name, type) VALUES (89, 'service_attr_map', 'java.util.LinkedHashMap');
INSERT INTO attr_names(id, attr_name, type) VALUES (90, 'service_attr_lstring', 'java.lang.LargeString');
INSERT INTO attr_names(id, attr_name, type) VALUES (91, 'service_attr_larray', 'java.lang.LargeArrayList');

INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_str', 'value1');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_int', '1');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_bool', 'true');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_array', '1,2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_map', 'key1:value1,key2:value2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_lstring', 'long_value1');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (1, 'ext_source_attr_larray', '1,2');

INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_str', 'value2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_int', '2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_bool', 'false');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_array', '3,4');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_map', 'key3:value3,key4:value4');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_lstring', 'long_value2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (2, 'ext_source_attr_larray', '3,4');

INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_str', 'value2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_int', '2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_bool', 'false');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_array', '3,4');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_map', 'key3:value3,key4:value4');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_lstring', 'long_value2');
INSERT INTO ext_sources_attributes(ext_sources_id, attr_name, attr_value) VALUES (23, 'ext_source_attr_larray', '3,4');

INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 'value1', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 2, '1', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 3, 'true', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 4, null, '1,2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 5, null, 'key1:value1,key2:value2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 6, null, 'long_value1');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 7, null, '1,2');

INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 1, 'value2', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, '2', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 3, 'false', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 4, null, '3,4');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 5, null, 'key3:value3,key4:value4');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 6, null, 'long_value2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 7, null, '3,4');

INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 1, 'value2', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 2, '2', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 3, 'false', null);
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 4, null, '3,4');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 5, null, 'key3:value3,key4:value4');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 6, null, 'long_value2');
INSERT INTO facility_attr_values(facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 7, null, '3,4');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 8, 'value1', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 9, '1', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 10, 'true', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 11, null, '1,2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 12, null, 'key1:value1,key2:value2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 13, null, 'long_value1');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (1, 14, null, '1,2');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 8, 'value2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 9, '2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 10, 'false', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 11, null, '3,4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 12, null, 'key3:value3,key4:value4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 13, null, 'long_value2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (2, 14, null, '3,4');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 8, 'value2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 9, '2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 10, 'false', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 11, null, '3,4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 12, null, 'key3:value3,key4:value4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 13, null, 'long_value2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (23, 14, null, '3,4');

INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 8, 'value2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 9, '2', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 10, 'false', null);
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 11, null, '3,4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 12, null, 'key3:value3,key4:value4');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 13, null, 'long_value2');
INSERT INTO group_attr_values(group_id, attr_id, attr_value, attr_value_text) VALUES (123, 14, null, '3,4');

INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 15, 'value1', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 16, '1', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 17, 'true', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 18, null, '1,2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 19, null, 'key1:value1,key2:value2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 20, null, 'long_value1');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (1, 21, null, '1,2');

INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 15, 'value2', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 16, '2', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 17, 'false', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 18, null, '3,4');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 19, null, 'key3:value3,key4:value4');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 20, null, 'long_value2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (2, 21, null, '3,4');

INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 15, 'value2', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 16, '2', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 17, 'false', null);
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 18, null, '3,4');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 19, null, 'key3:value3,key4:value4');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 20, null, 'long_value2');
INSERT INTO host_attr_values(host_id, attr_id, attr_value, attr_value_text) VALUES (23, 21, null, '3,4');

INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 22, 'value1', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 23, '1', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 24, 'true', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 25, null, '1,2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 26, null, 'key1:value1,key2:value2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 27, null, 'long_value1');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (1, 28, null, '1,2');

INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 22, 'value2', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 23, '2', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 24, 'false', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 25, null, '3,4');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 26, null, 'key3:value3,key4:value4');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 27, null, 'long_value2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (2, 28, null, '3,4');

INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 22, 'value2', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, '2', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 24, 'false', null);
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 25, null, '3,4');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 26, null, 'key3:value3,key4:value4');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 27, null, 'long_value2');
INSERT INTO member_attr_values(member_id, attr_id, attr_value, attr_value_text) VALUES (23, 28, null, '3,4');

INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 29, 'value1', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 30, '1', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 31, 'true', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 32, null, '1,2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 33, null, 'key1:value1,key2:value2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 34, null, 'long_value1');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 35, null, '1,2');

INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 29, 'value2', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 30, '2', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 31, 'false', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 32, null, '3,4');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 33, null, 'key3:value3,key4:value4');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 34, null, 'long_value2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 35, null, '3,4');

INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 29, 'value2', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 30, '2', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 31, 'false', null);
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 32, null, '3,4');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 33, null, 'key3:value3,key4:value4');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 34, null, 'long_value2');
INSERT INTO resource_attr_values(resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 35, null, '3,4');

INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 36, 'value1', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 37, '1', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 38, 'true', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 39, null, '1,2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 40, null, 'key1:value1,key2:value2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 41, null, 'long_value1');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (1, 42, null, '1,2');

INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 36, 'value2', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 37, '2', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 38, 'false', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 39, null, '3,4');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 40, null, 'key3:value3,key4:value4');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 41, null, 'long_value2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (2, 42, null, '3,4');

INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 36, 'value2', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 37, '2', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 38, 'false', null);
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 39, null, '3,4');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 40, null, 'key3:value3,key4:value4');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 41, null, 'long_value2');
INSERT INTO user_ext_source_attr_values(user_ext_source_id, attr_id, attr_value, attr_value_text) VALUES (23, 42, null, '3,4');

INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 43, 'value1', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 44, '1', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 45, 'true', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 46, null, '1,2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 47, null, 'key1:value1,key2:value2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 48, null, 'long_value1');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (1, 49, null, '1,2');

INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 43, 'value2', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 44, '2', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 45, 'false', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 46, null, '3,4');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 47, null, 'key3:value3,key4:value4');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 48, null, 'long_value2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (2, 49, null, '3,4');

INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 43, 'value2', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 44, '2', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 45, 'false', null);
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 46, null, '3,4');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 47, null, 'key3:value3,key4:value4');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 48, null, 'long_value2');
INSERT INTO user_attr_values(user_id, attr_id, attr_value, attr_value_text) VALUES (23, 49, null, '3,4');

INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 50, 'value1', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 51, '1', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 52, 'true', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 53, null, '1,2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 54, null, 'key1:value1,key2:value2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 55, null, 'long_value1');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (1, 56, null, '1,2');

INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 50, 'value2', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 51, '2', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 52, 'false', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 53, null, '3,4');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 54, null, 'key3:value3,key4:value4');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 55, null, 'long_value2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (2, 56, null, '3,4');

INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 50, 'value2', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 51, '2', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 52, 'false', null);
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 53, null, '3,4');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 54, null, 'key3:value3,key4:value4');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 55, null, 'long_value2');
INSERT INTO vo_attr_values(vo_id, attr_id, attr_value, attr_value_text) VALUES (23, 56, null, '3,4');

INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 57, 'value1', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 58, '1', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 59, 'true', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 60, null, '1,2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 61, null, 'key1:value1,key2:value2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 62, null, 'long_value1');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 63, null, '1,2');

INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 57, 'value2', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 58, '2', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 59, 'false', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 60, null, '3,4');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 61, null, 'key3:value3,key4:value4');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 62, null, 'long_value2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 63, null, '3,4');

INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 57, 'value2', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 58, '2', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 59, 'false', null);
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 60, null, '3,4');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 61, null, 'key3:value3,key4:value4');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 62, null, 'long_value2');
INSERT INTO group_resource_attr_values(group_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 63, null, '3,4');

INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 64, 'value1', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 65, '1', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 66, 'true', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 67, null, '1,2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 68, null, 'key1:value1,key2:value2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 69, null, 'long_value1');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 70, null, '1,2');

INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 64, 'value2', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 65, '2', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 66, 'false', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 67, null, '3,4');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 68, null, 'key3:value3,key4:value4');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 69, null, 'long_value2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 70, null, '3,4');

INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 64, 'value2', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 65, '2', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 66, 'false', null);
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 67, null, '3,4');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 68, null, 'key3:value3,key4:value4');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 69, null, 'long_value2');
INSERT INTO member_group_attr_values(member_id, group_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 70, null, '3,4');

INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 71, 'value1', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 72, '1', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 73, 'true', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 74, null, '1,2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 75, null, 'key1:value1,key2:value2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 76, null, 'long_value1');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 77, null, '1,2');

INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 71, 'value2', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 72, '2', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 73, 'false', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 74, null, '3,4');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 75, null, 'key3:value3,key4:value4');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 76, null, 'long_value2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 77, null, '3,4');

INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 71, 'value2', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 72, '2', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 73, 'false', null);
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 74, null, '3,4');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 75, null, 'key3:value3,key4:value4');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 76, null, 'long_value2');
INSERT INTO member_resource_attr_values(member_id, resource_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 77, null, '3,4');

INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 78, 'value1', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 79, '1', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 80, 'true', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 81, null, '1,2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 82, null, 'key1:value1,key2:value2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 83, null, 'long_value1');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (1, 1, 84, null, '1,2');

INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 78, 'value2', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 79, '2', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 80, 'false', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 81, null, '3,4');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 82, null, 'key3:value3,key4:value4');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 83, null, 'long_value2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (2, 2, 84, null, '3,4');

INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 78, 'value2', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 79, '2', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 80, 'false', null);
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 81, null, '3,4');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 82, null, 'key3:value3,key4:value4');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 83, null, 'long_value2');
INSERT INTO user_facility_attr_values(user_id, facility_id, attr_id, attr_value, attr_value_text) VALUES (23, 23, 84, null, '3,4');

INSERT INTO vo_ext_sources(vo_id, ext_sources_id) VALUES (1, 1);
INSERT INTO vo_ext_sources(vo_id, ext_sources_id) VALUES (2, 2);
INSERT INTO vo_ext_sources(vo_id, ext_sources_id) VALUES (23, 23);

INSERT INTO groups_resources(group_id, resource_id) VALUES(1, 1);
INSERT INTO groups_resources(group_id, resource_id) VALUES(2, 2);
INSERT INTO groups_resources(group_id, resource_id) VALUES(23, 23);

INSERT INTO resource_services(resource_id, service_id) VALUES (1, 1);
INSERT INTO resource_services(resource_id, service_id) VALUES (2, 2);
INSERT INTO resource_services(resource_id, service_id) VALUES (23, 23);

INSERT INTO groups_members(group_id, member_id) VALUES (1, 1);
INSERT INTO groups_members(group_id, member_id) VALUES (2, 2);
INSERT INTO groups_members(group_id, member_id) VALUES (23, 23);

INSERT INTO group_ext_sources(group_id, ext_source_id) VALUES (1, 1);
INSERT INTO group_ext_sources(group_id, ext_source_id) VALUES (2, 2);
INSERT INTO group_ext_sources(group_id, ext_source_id) VALUES (23, 23);
