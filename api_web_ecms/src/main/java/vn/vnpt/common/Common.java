///*******************************************************************************
// * Copyright (c) 2017 ANHTCN.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *******************************************************************************/
//
//package vn.vnpt.common;
//
//import org.apache.commons.lang3.time.DateUtils;
//import com.fasterxml.uuid.Generators;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Function;
//import java.util.function.Predicate;
//
//import javax.imageio.ImageIO;
//
///**
// * @author trananh
// *
// */
//public class Common {
//
//	public static final class MINIO_CLIENT {
//
//		public static final long CONNECT_TIMEOUT_MS = 60000;
//		public static final long WRITE_TIMEOUT_MS = 60000;
//		public static final long READ_TIMEOUT_MS = 60000;
//	}
//	private static final Random random = new Random();
//	public static String GenerateUUID() {
//		UUID uuid2 = Generators.timeBasedGenerator().generate();
//		return uuid2.toString();
//	}
//
//	public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
//		try {
//			return clazz.cast(o);
//		} catch (ClassCastException e) {
//			return null;
//		}
//	}
//
//	public static String encryptPassword(String passwordToHash) {
//		try {
//			byte[] encoded = MessageDigest.getInstance("SHA-1").digest(passwordToHash.getBytes());
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < encoded.length; i++) {
//				sb.append(Integer.toString((encoded[i] & 0xff) + 0x100, 16).substring(1));
//			}
//
//			return sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			return "";
//		}
//	}
//
//	public static Timestamp convertStringToTimestamp(String str_date) {
//		try {
//			DateFormat formatter;
//			formatter = new SimpleDateFormat("MM/dd/yyyy");
//			// you can change format of date
//			Date date = formatter.parse(str_date);
//			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
//
//			return timeStampDate;
//		} catch (ParseException e) {
//			System.out.println("Exception :" + e);
//			return null;
//		}
//	}
//
//	public static Date convertCurrentTimeLongToDateGMT(long currentTimeMillis) {
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss");
//		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//		// Date date2 = new Date(currentTimeMillis);
//		return new Date(currentTimeMillis);
//
//	}
//
//	public static Date convertStringToDateGMT(String date, String pattern) throws ParseException {
//		if (date == null || pattern == null) {
//			return null;
//		} else {
////			TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//			return simpleDateFormat.parse(date);
//		}
//
//	}
////
////	public static String subString(String str) {
////		if (isNullOrEmpty(str))
////			return "";
////
////		if (str.length() > 100) {
////			return str.substring(ConstantString.NUMBER_ZERO, ConstantString.MAX_SIZE);
////		}
////		return str;
////
////	}
////	public static String imageConvertMethod(String url) throws Exception {
////		ByteArrayOutputStream output = new ByteArrayOutputStream();
////
////		try (InputStream input = new URL(url).openStream()) {
////			byte[] buffer = new byte[512];
////			for (int length = 0; (length = input.read(buffer)) > 0;) {
////				output.write(buffer, 0, length);
////			}
////		}
////
////		byte[] byte_array = output.toByteArray();
////
////		byte[] img64 = com.sun.jersey.core.util.Base64.encode(byte_array);
////		String imageString = new String(img64);
////
////		return imageString;
////	}
//
//	public static final Date getCurrentDate() {
//		return DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
//	}
//
//	public static Date getCurrentTime() {
//		return new Date();
//	}
//
//	public static LocalDate convertToLocalDateViaInstant() {
//		Date dateToConvert = new Date();
//		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	}
//
//	public static Date getDate() {
//		return new Date();
//	}
//
//	public static Instant getInstant() {
//		return Instant.now();
//	}
//
//	public static long getCurrentTimeAddFive() {
//		return System.currentTimeMillis() + 5 * 60 * 1000;
//	}
//
//	public static long getCurrentTimeLong() {
//		return System.currentTimeMillis();
//	}
//
//	public static String convertDateToString(Date date, String pattern) {
//
//		if (date == null) {
//			return "";
//		} else {
//			SimpleDateFormat plainDateFormat = new SimpleDateFormat(pattern);
//			return plainDateFormat.format(date);
//		}
//	}
//
//	public static Date convertStringToDate(String date, String pattern) throws ParseException {
//
//		if (date == null || pattern == null) {
//			return null;
//		} else {
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//			return simpleDateFormat.parse(date);
//		}
//
//	}
//
//	public static long convertDateToEpoch(String str, String pattern) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = null;
//		try {
//			date = df.parse(str);
//		} catch (ParseException e) {
//			return 0;
//		}
//		return date.getTime();
//
//	}
//
//	public static BufferedImage decodeToImage(String imageString) throws IOException {
//		BufferedImage image = null;
//		// byte[] imageByte;
//		// BASE64Decoder decoder = new BASE64Decoder();
//		Base64.Decoder base64Decoder = Base64.getDecoder();
//		byte[] imageByte = base64Decoder.decode(imageString.getBytes("UTF-8"));
//
//		// imageByte = decoder.decodeBuffer(imageString);
//		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//		image = ImageIO.read(bis);
//		bis.close();
//		return image;
//	}
//
//	public static String getImageType(String base64) {
//		String[] header = base64.split("[;]");
//		if (header == null || header.length == 0)
//			return null;
//		return header[0].split("[/]")[1];
//	}
//
//	public static String removeBase64Header(String base64) {
//		if (base64 == null)
//			return null;
//		return base64.trim().replaceFirst("data[:]image[/]([a-z])+;base64,", "");
//	}
//
//	public static String createFileImage(String base64, String fileName) throws IOException {
//		// convert base64 string to image
//		try {
//			String imageString = removeBase64Header(base64);
//			String imageType = getImageType(base64);
//			BufferedImage image = decodeToImage(imageString);
//
//			ImageIO.write(image, imageType,
//					new File("src/main/resources/static/image_product/" + fileName + "." + imageType));
//			return "image_product/" + fileName + "." + imageType;
//		} catch (Exception e) {
//			return null;
//		}
//
//	}
//
//
//	public static Timestamp convertStringToTimestamp1(String something) throws ParseException {
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	    Timestamp timestamp = null;
//
//	    if (something == null || something.isEmpty()) {
//	        return Timestamp.valueOf("1990-01-01 00:00:00");
//	    }
//
//	    if (something.contains(",")) {
//	        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
//	    }
//
//	    try {
//	        Date parsedDate = dateFormat.parse(something);
//	        timestamp = new java.sql.Timestamp(parsedDate.getTime());
//	    } catch (ParseException e) {
//	        System.out.println("ParseException: " + e.getMessage());
//	        timestamp = Timestamp.valueOf("1990-01-01 00:00:00");
//	    }
//
//	    return timestamp;
//	}
//
//
//	public static String getAlphaNumeric(int len) {
//		char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
//				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
//				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
//				'z' };
//
//		char[] c = new char[len];
//		
//		for (int i = 0; i < len; i++) {
//			c[i] = ch[random.nextInt(ch.length)];
//		}
//
//		return new String(c);
//	}
//
//	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
//		Set<Object> seen = ConcurrentHashMap.newKeySet();
//		return t -> seen.add(keyExtractor.apply(t));
//	}
//
//	/**
//	 * Check if String with spaces is Empty or Null
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static boolean isNullOrEmpty(String str) {
//		if (str != null && (!str.trim().isEmpty()) && ("".equals(str.trim())))
//			return false;
//		return true;
//	}
//
//	public static String getUserId() {
//		String userId = "";
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null) {
//			Object details = authentication.getDetails();
//			if (details instanceof OAuth2AuthenticationDetails) {
//				Object decodedDetails = ((OAuth2AuthenticationDetails) details).getDecodedDetails();
//				if (decodedDetails instanceof Map) {
//					userId = (String) ((Map<?, ?>) decodedDetails).get("uuid_account");
//				}
//			}
//		}
//		return userId;
//	}
//}
