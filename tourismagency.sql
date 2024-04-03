-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 04 Şub 2024, 19:43:35
-- Sunucu sürümü: 8.0.31
-- PHP Sürümü: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `tourismagency`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_featuretype`
--

CREATE TABLE `tbl_featuretype` (
  `id` int NOT NULL,
  `feature_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_featuretype`
--

INSERT INTO `tbl_featuretype` (`id`, `feature_name`) VALUES
(1, 'Ücretsiz Otopark'),
(2, 'SPA'),
(3, '7/24 Oda Servisi'),
(4, 'Ücretsiz WiFi'),
(5, 'Yüzme Havuzu'),
(6, 'Fitness Center'),
(7, 'Hotel Concierge');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_hotel`
--

CREATE TABLE `tbl_hotel` (
  `hotel_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `city` varchar(150) NOT NULL,
  `region` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  `stars` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_hotel`
--

INSERT INTO `tbl_hotel` (`hotel_id`, `name`, `city`, `region`, `address`, `email`, `phoneNumber`, `stars`) VALUES
(2, 'Rocken', 'Zürich', 'Sweden', 'zürich st 15', 'rockenz@gmail.com', '05555555555', 3),
(7, 'Golden', 'İstanbul', 'merkez', 'golden sk', 'golden@com', '19199191', 8),
(8, 'Leman', 'İzmir', 'Ulus', 'lale Sokak', 'leman@com', '06060606060', 8),
(9, 'Hotelz', 'Trabzon', 'Of', 'Hotel Z No:61', 'hotelz@61.com', '6161616161', 5),
(11, 'Nirvana Hotel', 'Antalya', 'Alanya', 'alanya sk', 'nirvana@mail', '778678687678', 5),
(14, 'aaaaaa', 'aaaaa', 'aaaa', 'aaaaa', 'aaaa', 'aaaa', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_hotel_feature`
--

CREATE TABLE `tbl_hotel_feature` (
  `hotel_id` int NOT NULL,
  `feature_id` int NOT NULL,
  `feature_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_hotel_feature`
--

INSERT INTO `tbl_hotel_feature` (`hotel_id`, `feature_id`, `feature_name`) VALUES
(3, 1, 'Ücretsiz Otopark'),
(3, 3, '7/24 Oda Servisi'),
(3, 4, 'Ücretsiz WiFi'),
(2, 1, 'Ücretsiz Otopark'),
(4, 6, 'Fitness Center'),
(1, 3, '7/24 Oda Servisi'),
(1, 5, 'Yüzme Havuzu'),
(5, 1, 'Ücretsiz Otopark'),
(5, 4, 'Ücretsiz WiFi'),
(5, 6, 'Fitness Center'),
(6, 1, 'Ücretsiz Otopark'),
(6, 5, 'Yüzme Havuzu');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_hotel_pension`
--

CREATE TABLE `tbl_hotel_pension` (
  `id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `pension_id` int NOT NULL,
  `pension_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_hotel_pension`
--

INSERT INTO `tbl_hotel_pension` (`id`, `hotel_id`, `pension_id`, `pension_name`) VALUES
(1, 1, 1, 'Ultra Herşey Dahil'),
(2, 1, 4, 'Tam Pansiyon'),
(3, 3, 1, 'Ultra Herşey Dahil'),
(4, 3, 5, 'Yarım Pansiyon'),
(5, 2, 4, 'Tam Pansiyon'),
(6, 4, 7, 'Alkol Hariç Full credit'),
(7, 5, 1, 'Ultra Herşey Dahil'),
(8, 5, 4, 'Tam Pansiyon'),
(9, 6, 2, 'Herşey Dahil'),
(10, 6, 4, 'Tam Pansiyon'),
(11, 2, 3, 'Oda Kahvaltı');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_hotel_room`
--

CREATE TABLE `tbl_hotel_room` (
  `room_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `season_id` int NOT NULL,
  `pension_id` int NOT NULL,
  `room_type` varchar(150) NOT NULL,
  `bed_number` int NOT NULL,
  `stock` int NOT NULL,
  `price_child` int NOT NULL,
  `price_adult` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_hotel_room`
--

INSERT INTO `tbl_hotel_room` (`room_id`, `hotel_id`, `season_id`, `pension_id`, `room_type`, `bed_number`, `stock`, `price_child`, `price_adult`) VALUES
(5, 2, 6, 5, 'Double', 70, 999998, 200, 500),
(13, 2, 6, 5, 'sdf', 5, 10, 10, 10),
(16, 2, 6, 4, '123', 2, 12, 10, 100);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_pensiontype`
--

CREATE TABLE `tbl_pensiontype` (
  `id` int NOT NULL,
  `pension_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_pensiontype`
--

INSERT INTO `tbl_pensiontype` (`id`, `pension_name`) VALUES
(1, 'Ultra Herşey Dahil'),
(2, 'Herşey Dahil'),
(3, 'Oda Kahvaltı'),
(4, 'Tam Pansiyon'),
(5, 'Yarım Pansiyon'),
(6, 'Sadece Yatak'),
(7, 'Alkol Hariç Full credit');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_reservation`
--

CREATE TABLE `tbl_reservation` (
  `reservation_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `room_id` int NOT NULL,
  `customer_name` varchar(150) NOT NULL,
  `customer_tc` varchar(150) NOT NULL,
  `customer_phone` varchar(150) NOT NULL,
  `customer_email` varchar(150) NOT NULL,
  `child_number` int NOT NULL,
  `adult_number` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_reservation`
--

INSERT INTO `tbl_reservation` (`reservation_id`, `hotel_id`, `room_id`, `customer_name`, `customer_tc`, `customer_phone`, `customer_email`, `child_number`, `adult_number`) VALUES
(2, 2, 2, 'Michael J', '11111111111', '0000-0000', 'michael@gmail', 10, 3),
(7, 8, 9, 'Jack Barrow', '84584358374', '27372992595694', 'jack@barrow', 5, 2),
(10, 2, 5, 'Emily B', '9139183918', '2072017', 'emily@b', 0, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_room_property`
--

CREATE TABLE `tbl_room_property` (
  `room_id` int NOT NULL,
  `property_name` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_room_property`
--

INSERT INTO `tbl_room_property` (`room_id`, `property_name`) VALUES
(1, 'Televizyon'),
(1, 'Minibar'),
(1, 'Kasa'),
(5, 'Ücretsiz Wifi'),
(3, 'Aynalı'),
(4, 'Wifi');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_season`
--

CREATE TABLE `tbl_season` (
  `season_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `season_name` varchar(150) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_season`
--

INSERT INTO `tbl_season` (`season_id`, `hotel_id`, `season_name`, `start_date`, `end_date`) VALUES
(6, 2, 'Kış Sezonu', '2023-03-01', '2023-06-01');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id` int NOT NULL,
  `tcNo` varchar(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `usertype` enum('admin','employee','','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Tablo döküm verisi `tbl_user`
--
INSERT INTO `tbl_user` (`id`, `tcNo`, `username`, `password`, `name`, `surname`, `usertype`) VALUES
(1, '10278912345', 'code_edanur', 'isvicre', 'Edanur', 'Gurgen', 'admin'),
(2, '11198715924', 'alistes', 'love', 'Ali', 'Deniz', 'employee'),
(4, '29873654211', 'duman', 'su', 'Duman', 'Su', 'employee');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `tbl_featuretype`
--
ALTER TABLE `tbl_featuretype`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `tbl_hotel`
--
ALTER TABLE `tbl_hotel`
  ADD PRIMARY KEY (`hotel_id`);

--
-- Tablo için indeksler `tbl_hotel_pension`
--
ALTER TABLE `tbl_hotel_pension`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `tbl_hotel_room`
--
ALTER TABLE `tbl_hotel_room`
  ADD PRIMARY KEY (`room_id`);

--
-- Tablo için indeksler `tbl_pensiontype`
--
ALTER TABLE `tbl_pensiontype`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `tbl_reservation`
--
ALTER TABLE `tbl_reservation`
  ADD PRIMARY KEY (`reservation_id`);

--
-- Tablo için indeksler `tbl_season`
--
ALTER TABLE `tbl_season`
  ADD PRIMARY KEY (`season_id`);

--
-- Tablo için indeksler `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `tbl_featuretype`
--
ALTER TABLE `tbl_featuretype`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_hotel`
--
ALTER TABLE `tbl_hotel`
  MODIFY `hotel_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_hotel_pension`
--
ALTER TABLE `tbl_hotel_pension`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_hotel_room`
--
ALTER TABLE `tbl_hotel_room`
  MODIFY `room_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_pensiontype`
--
ALTER TABLE `tbl_pensiontype`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_reservation`
--
ALTER TABLE `tbl_reservation`
  MODIFY `reservation_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_season`
--
ALTER TABLE `tbl_season`
  MODIFY `season_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Tablo için AUTO_INCREMENT değeri `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;