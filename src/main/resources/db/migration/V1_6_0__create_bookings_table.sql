
CREATE SEQUENCE bookings_seq;

CREATE TABLE bookings (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('bookings_seq'),
  price_before_tax decimal NOT NULL,
  price_after_tax decimal NOT NULL,
  currency character(3) NOT NULL,
  guest_id bigint check (guest_id > 0) NOT NULL,
  payment_id bigint check (payment_id > 0) NOT NULL,
  hotel_id bigint check (hotel_id > 0) NOT NULL,
  room_id bigint check (room_id > 0) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT bookings_hotel_id_foreign FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
  CONSTRAINT bookings_room_id_foreign FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE
);


CREATE INDEX bookings_hotel_id_foreign ON bookings (hotel_id);
CREATE INDEX bookings_room_id_foreign ON bookings (room_id);
