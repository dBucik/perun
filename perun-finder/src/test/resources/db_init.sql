create table attr_names (
	id integer not null,
	attr_name varchar(384) not null,
	type varchar(256) not null,
	friendly_name varchar(256),
	namespace varchar(256),
	constraint attnam_pk primary key(id),
	constraint attnam_u unique (attr_name),
	constraint attfullnam_u unique (friendly_name,namespace)
);

create table ext_sources (
	id integer not null,
	name varchar(256) not null,
	type varchar(64),
	constraint usrsrc_pk primary key(id),
	constraint usrsrc_u unique (name)
);

create table vos (
	id integer not null,
	name varchar(128) not null,
	short_name varchar(32) not null,
	constraint vo_pk primary key (id),
	constraint vo_u unique (name)
);

create table services (
	id integer not null,
	name varchar(128) not null,
	description varchar(1024),
	delay integer not null default 10,
	recurrence integer not null default 2,
	enabled char(1) not null default '1',
	script varchar(256) not null,
	constraint serv_pk primary key(id),
	constraint serv_u unique(name)
);


create table users (
	id integer not null,
	first_name varchar(64),
	last_name varchar(64),
	middle_name varchar(64),
	title_before varchar(40),
	title_after varchar(40),
	service_acc char(1) default '0' not null,
	sponsored_acc char(1) default '0' not null,
	constraint usr_pk primary key (id),
	constraint usr_srvacc_chk check (service_acc in ('0','1'))
);


create table facilities (
	id integer not null,
	name varchar(128) not null,
	dsc varchar(1024),
	constraint fac_pk primary key(id),
	constraint fac_name_u unique (name)
);

create table hosts (
	id integer not null,
	hostname varchar(128) not null,
	facility_id integer not null,
	dsc varchar(1024),
	constraint host_pk primary key (id),
	constraint host_fac_fk foreign key(facility_id) references facilities(id)
);


create table resources (
	id integer not null,
	facility_id integer not null,
	name varchar(128) not null,
	dsc varchar(1024),
	vo_id integer not null,
	constraint rsrc_pk primary key (id),
	constraint rsrc_fac_fk foreign key (facility_id) references facilities(id),
	constraint rsrc_vo_fk foreign key (vo_id) references vos(id)
);

create table groups (
	id integer not null,
	name text not null,
	dsc varchar(1024),
	vo_id integer not null,
	parent_group_id integer,
	constraint grp_pk primary key (id),
	constraint grp_nam_vo_parentg_u unique (name,vo_id,parent_group_id),
	constraint grp_vos_fk foreign key (vo_id) references vos(id),
	constraint grp_grp_fk foreign key (parent_group_id) references groups(id)
);

create table user_ext_sources (
	id integer not null,
	user_id integer not null,
	login_ext varchar(1300) not null,
	ext_sources_id integer not null,
	loa integer,
	last_access timestamp default statement_timestamp() not null,
	constraint usrex_p primary key(id),
	constraint usrex_u unique (ext_sources_id,login_ext),
	constraint usrex_usr_fk foreign key (user_id) references users(id),
	constraint usrex_usersrc_fk foreign key(ext_sources_id) references ext_sources(id)
);

create table members (
	id integer not null,
	user_id integer not null,
	vo_id integer not null,
	sponsored boolean default false not null,
	constraint mem_pk primary key(id),
	constraint mem_user_fk foreign key(user_id) references users(id),
	constraint mem_vo_fk foreign key(vo_id) references vos(id),
	constraint mem_user_vo_u unique (vo_id, user_id)
);


create table ext_sources_attributes (
	ext_sources_id integer not null,
	attr_name varchar(128) not null,
	attr_value varchar(4000),
	constraint usrcatt_usrc_fk foreign key (ext_sources_id) references ext_sources(id)
);

create table facility_attr_values (
	facility_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint facattval_pk primary key (facility_id,attr_id),
	constraint facattval_nam_fk foreign key (attr_id) references attr_names(id),
	constraint facattval_fac_fk foreign key (facility_id) references facilities (id)
);

create table group_attr_values (
	group_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	modified_by_uid integer,
	constraint grpattval_pk primary key (group_id,attr_id),
	constraint grpattval_grp_fk foreign key (group_id) references groups(id),
	constraint grpattval_attr_fk foreign key (attr_id) references attr_names(id)
);

create table host_attr_values (
	host_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint hostav_pk primary key (host_id,attr_id),
	constraint hostav_host_fk foreign key (host_id) references hosts(id),
	constraint hostav_attr_fk foreign key (attr_id) references attr_names(id)
);

create table member_attr_values (
	member_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint memattval_pk primary key (member_id,attr_id),
	constraint memattval_mem_fk foreign key (member_id) references members(id),
	constraint memattval_attr_fk foreign key (attr_id) references attr_names(id)
);

create table resource_attr_values (
	resource_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint resatval_pk primary key (resource_id,attr_id),
	constraint resatval_res_fk foreign key(resource_id) references resources(id),
	constraint resatval_resatnam_fk foreign key(attr_id) references attr_names(id)
);

create table user_attr_values (
	user_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint usrav_pk primary key(user_id,attr_id),
	constraint usrav_usr_fk foreign key (user_id) references users(id),
	constraint usrav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

create table vo_attr_values (
	vo_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint voattval_pk primary key (vo_id,attr_id),
	constraint voattval_nam_fk foreign key (attr_id) references attr_names(id),
	constraint voattval_vo_fk foreign key (vo_id) references vos (id)
);

create table group_resource_attr_values (
	group_id integer not null,
	resource_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint grpresav_pk primary key (group_id,resource_id,attr_id),
	constraint grpresav_grp_fk foreign key (group_id) references groups(id),
	constraint grpresav_res_fk foreign key (resource_id) references resources(id),
	constraint grpresav_attr_fk foreign key (attr_id) references attr_names(id)
);

create table user_ext_source_attr_values (
	user_ext_source_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint uesattrval_pk primary key (user_ext_source_id, attr_id),
  constraint uesattrval_ues_fk foreign key (user_ext_source_id) references user_ext_sources(id),
  constraint uesattrval_attr_fk foreign key (attr_id) references attr_names(id)
);


create table member_group_attr_values (
	member_id integer not null,
	group_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint memgav_pk primary key(member_id,group_id,attr_id),
	constraint memgav_mem_fk foreign key (member_id) references members(id),
	constraint memgav_grp_fk foreign key (group_id) references groups(id),
	constraint memgav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

create table member_resource_attr_values (
	member_id integer not null,
	resource_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint memrav_pk primary key(member_id,resource_id,attr_id),
	constraint memrav_mem_fk foreign key (member_id) references members(id),
	constraint memrav_rsrc_fk foreign key (resource_id) references resources(id),
	constraint memrav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

CREATE TABLE member_resource_attr_u_values (
	member_id INT NOT NULL,
	resource_id INT NOT NULL,
	attr_id INT NOT NULL,
	attr_value VARCHAR(4000),
	UNIQUE (attr_id, attr_value),
	FOREIGN KEY (member_id,resource_id,attr_id) REFERENCES member_resource_attr_values ON DELETE CASCADE
);

create table user_facility_attr_values (
	user_id integer not null,
	facility_id integer not null,
	attr_id integer not null,
	attr_value varchar(4000),
	attr_value_text text,
	constraint usrfacav_u primary key(user_id,facility_id,attr_id),
	constraint usrfacav_mem_fk foreign key (user_id) references users(id),
	constraint usrfacav_fac_fk foreign key (facility_id) references facilities(id),
	constraint usrfacav_accattnam_fk foreign key (attr_id) references attr_names(id)
);

create table vo_ext_sources (
	vo_id integer not null,
	ext_sources_id integer not null,
	constraint vousrsrc_pk primary key (vo_id,ext_sources_id),
  constraint vousrsrc_usrsrc_fk foreign key(ext_sources_id) references ext_sources(id),
  constraint vousrsrc_vos_fk foreign key(vo_id) references vos(id)
);

create table groups_resources (
	group_id integer not null,
	resource_id integer not null,
	constraint grres_grp_res_u unique (group_id,resource_id),
  constraint grres_gr_fk foreign key (group_id) references groups(id),
  constraint grres_res_fk foreign key (resource_id) references resources(id)
);

create table resource_services (
	service_id integer not null,
	resource_id integer not null,
	constraint resrcsrv_pk primary key (service_id,resource_id),
  constraint resrcsrv_srv_fk foreign key (service_id) references services(id),
  constraint resrcsrv_rsrc_fk foreign key (resource_id) references resources(id)
);

create table groups_members (
	group_id integer not null,
	member_id integer not null,
  constraint grpmem_gr_fk foreign key (group_id) references groups(id),
  constraint grpmem_mem_fk foreign key (member_id) references members(id)
);

create table group_ext_sources (
	group_id integer not null,
	ext_source_id integer not null,
	constraint groupsrc_pk primary key (group_id,ext_source_id),
  constraint groupsrc_src_fk foreign key(ext_source_id) references ext_sources(id),
  constraint groupsrc_groups_fk foreign key(group_id) references groups(id)
);