drop tablespace OFMP_DATA including contents and datafiles cascade constraints;

create tablespace OFMP_DATA
datafile 'OFMP_data.dbf' 
size 10M reuse
 autoextend on;

drop tablespace OFMP_IDX including contents and datafiles cascade constraints;

create tablespace OFMP_IDX
datafile 'OFMP_idx.dbf'
size 5M reuse
 autoextend on;
/

exit