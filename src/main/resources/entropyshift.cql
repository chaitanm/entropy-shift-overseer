CREATE KEYSPACE entropyshift WITH replication = {'class':'SimpleStrategy', 'replication_factor':1 };

USE entropyshift;

CREATE TABLE oauth_authorization (
  authorization_code_hash blob PRIMARY KEY,
  client_id uuid,
  user_id text,
  redirect_uri varchar,
  scope varchar,
  client_state varchar,
  expires bigint,
  access_token_hash blob,
  refresh_token_hash blob,
  created_timestamp bigint,
  user_validated Boolean
);

CREATE INDEX oauth_authorization_client_id_index ON oauth_authorization (client_id);
CREATE INDEX oauth_authorization_user_id_index ON oauth_authorization (user_id);

CREATE TABLE oauth_access(
 access_token_hash blob PRIMARY KEY,
 client_id uuid,
 user_id text,
 scope varchar,
 client_state varchar,
 expires bigint,
 created_timestamp bigint
);

CREATE INDEX oauth_access_client_id_index ON oauth_access (client_id);
CREATE INDEX oauth_access_user_id_index ON oauth_access (user_id);


CREATE TABLE oauth_refresh(
 refresh_token_hash blob PRIMARY KEY,
 access_token_hash blob,
 client_id uuid,
 user_id text,
 scope varchar,
 client_state varchar,
 expires bigint,
 issued_next_access_token_hash blob,
 issued_next_refresh_token_hash blob,
 created_timestamp bigint
);

CREATE INDEX oauth_refresh_client_id_index ON oauth_refresh (client_id);
CREATE INDEX oauth_refresh_user_id_index ON oauth_refresh (user_id);
CREATE INDEX oauth_refresh_access_token_hash_index ON oauth_refresh (access_token_hash);

CREATE TABLE auth_session(
 session_key_hash blob PRIMARY KEY,
 user_id text,
 ip_address text,
 device_id uuid,
 browser_id uuid,
 scope varchar,
 expires bigint,
 absolute_expiry bigint,
 created_timestamp bigint
);

CREATE INDEX auth_session_user_id_index ON auth_session(user_id);

CREATE TYPE entropyshift.name(
 first_name text,
 last_name text,
 middle_name text);

CREATE TYPE entropyshift.address(
 line1 text,
 line2 text,
 line3 text,
 postal text,
 province text,
 city text,
 state text,
 country text,
 zip_code int,
 zip_plus4 int,
 attention_line1 text,
 attention_line2 text,
 address_category text,
 effective_date bigint,
 updated_date bigint,
 termination_date bigint);

CREATE TYPE entropyshift.phone_number(
 country_code text,
 national_identification_number text,
 category text);

CREATE TYPE entropyshift.geolocation(
 latitude double,
 longitude double,
 altitude double,
 accuracy double,
 altitude_accuracy double,
 heading double,
 speed double);

CREATE TABLE entropyshift.user_profile(
 user_id text,
 address_information_list set<frozen<entropyshift.address>>,
 date_of_birth timestamp,
 email_address text,
 last_login_timestamp bigint,
 last_password_update_timestamp bigint,
 name_information frozen<entropyshift.name>,
 password_rejection_count int,
 phone_number_information_list set<frozen<entropyshift.phone_number>>,
 registered_devices set<uuid>,
 registration_browser_id uuid,
 registration_device_id uuid,
 registration_geo_location frozen<entropyshift.geolocation>,
 registration_ip_address text,
 registration_timestamp bigint,
 status text,
 uuid uuid,
 PRIMARY KEY(user_id));

CREATE INDEX user_profile_uuid_index ON user_profile(uuid);
CREATE INDEX user_profile_email_index ON user_profile(email_address);

