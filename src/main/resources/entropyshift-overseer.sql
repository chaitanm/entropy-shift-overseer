
CREATE TABLE configuration(
  name VARCHAR(200) PRIMARY KEY NOT NULL,
  value VARCHAR (1000) NOT NULL ,
  created_username VARCHAR (100) NOT NULL,
  updated_username VARCHAR(100),
  created_timestamp BIGINT,
  updated_timestamp BIGINT
);


CREATE TABLE user_credentials(
  uuid UUID PRIMARY KEY NOT NULL,
  username VARCHAR(100) NOT NULL,
  password_hash bytea NOT NULL,
  password_salt bytea NOT NULL,
  password_key_derivation_function VARCHAR(50) NOT NULL,
  password_hash_algorithm VARCHAR(50) NOT NULL,
  password_hash_iteration_count INT NOT NULL,
  password_hash_derived_key_length_in_bits INT NOT NULL,
  password_salt_length_in_bytes INT NOT NULL
);

CREATE TABLE elliptical_curve_key_information(
   id UUID PRIMARY KEY NOT NULL,
   x VARCHAR(100) NOT NULL,
   y VARCHAR(100) NOT NULL,
   d VARCHAR(100) NOT NULL,
   curve VARCHAR(50) NOT NULL
);

CREATE TABLE rsa_key_information(
   id UUID PRIMARY KEY NOT NULL,
   n VARCHAR(1000) NOT NULL,
   e VARCHAR(1000) NOT NULL,
   d VARCHAR(1000) NOT NULL,
   p VARCHAR(1000) NOT NULL,
   q VARCHAR(1000) NOT NULL,
   dp VARCHAR(1000) NOT NULL,
   dq VARCHAR(1000) NOT NULL,
   qi VARCHAR(1000) NOT NULL
);

CREATE TABLE jwt_app_key_meta_information(
   id SERIAL PRIMARY KEY NOT NULL,
   encryption_and_decryption_key_id UUID NOT NULL,
   encryption_and_decryption_method VARCHAR(50) NOT NULL,
   signature_and_verification_key_id UUID NOT NULL,
   signature_and_verification_method VARCHAR(50) NOT NULL,
   content_encryption_algorithm VARCHAR(50) NOT NULL,
   start_time BIGINT NOT NULL,
   end_time BIGINT NOT NULL
);
INSERT INTO configuration(name, value, created_username, created_timestamp) VALUES ('CASSANDRA_HOST','ec2-54-91-32-81.compute-1.amazonaws.com','chaitanya_m', 1484833120);
INSERT INTO configuration(name, value, created_username, created_timestamp) VALUES ('CASSANDRA_CQL_PORT','9042','chaitanya_m', 1484833120);
INSERT INTO configuration(name, value, created_username, created_timestamp) VALUES ('CASSANDRA_CLUSTER_NAME','Entropy Shift','chaitanya_m', 1484833120);

INSERT INTO configuration(name, value, created_username, created_timestamp) VALUES ('OAUTH_ISSUER','Endurance','chaitanya_m', 1484833120);