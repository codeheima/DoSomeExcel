package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectUtil {

	protected static final Map<Class<?>, String> cachedNames = new HashMap<Class<?>, String>();

	
	
	public static String getStreamData(String path){
		InputStream is = getResourceAsStream(path);
		return IOUtil.getStreamData(is);
	}
	
	public static URL getResource(String name) {
		URL url = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			url = classLoader.getResource(name);
		}
		if (url == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			url = classLoader.getResource(name);
			if (url == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtil.class.getClassLoader();
				url = classLoader.getResource(name);
			}
		}
		try {
			url = new URL(url.toURI().toASCIIString());
		} catch (Exception e) {
			throw new RuntimeException("error url:" + url, e);
		}
		return url;
	}

	public static InputStream getResourceAsStream(String name) {
		InputStream resourceStream = null;
		ClassLoader classLoader = getCustomClassLoader();
		if (classLoader != null) {
			resourceStream = classLoader.getResourceAsStream(name);
		}
		if (resourceStream == null) {
			// Try the current Thread context classloader
			classLoader = Thread.currentThread().getContextClassLoader();
			resourceStream = classLoader.getResourceAsStream(name);
			if (resourceStream == null) {
				// Finally, try the classloader for this class
				classLoader = ReflectUtil.class.getClassLoader();
				resourceStream = classLoader.getResourceAsStream(name);
			}
		}
		return resourceStream;
	}



	/**
	 * findMethod
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Method findMethod(Class<? extends Object> clazz,
			String methodName, Object[] args) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)
					&& matches(method.getParameterTypes(), args)) {
				return method;
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return findMethod(superClass, methodName, args);
		}
		return null;
	}

	public static boolean matches(Class<?>[] parameterTypes, Object[] args) {
		if ((parameterTypes == null) || (parameterTypes.length == 0)) {
			return ((args == null) || (args.length == 0));
		}
		if ((args == null) || (parameterTypes.length != args.length)) {
			return false;
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			if ((args[i] != null)
					&& (!parameterTypes[i].isAssignableFrom(args[i].getClass()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the field of the given class or null if it doesnt exist.
	 */
	public static Field getField(String fieldName, Class<?> clazz) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new RuntimeException("not allowed to access field " + field
					+ " on class " + clazz.getCanonicalName());
		} catch (NoSuchFieldException e) {
			// for some reason getDeclaredFields doesnt search superclasses
			// (which getFields() does ... but that gives only public fields)
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null) {
				return getField(fieldName, superClass);
			}
		}
		return field;
	}

	public static void setField(Field field, Object object, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not set field "
					+ field.toString(), e);
		}
	}

	public static ClassLoader getCustomClassLoader() {
		// TODO customerContext.getClassLoader()

		return null;
	}

	public static BufferedReader getBufferReader(File file) throws IOException {
		return new BufferedReader(new FileReader(file));
	}

	public static String getClassNameWithoutPackage(Object object) {
		return getClassNameWithoutPackage(object.getClass());
	}

	public static String getClassNameWithoutPackage(Class<?> clazz) {
		String unqualifiedClassName = cachedNames.get(clazz);
		if (unqualifiedClassName == null) {
			String fullyQualifiedClassName = clazz.getName();
			unqualifiedClassName = fullyQualifiedClassName
					.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
			cachedNames.put(clazz, unqualifiedClassName);
		}
		return unqualifiedClassName;
	}

	/**
	 * 浠庡寘package涓幏鍙栨墍鏈夌殑Class
	 * 
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String pack) {

		// 绗竴涓猚lass绫荤殑闆嗗悎
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 鏄惁寰幆杩唬
		boolean recursive = true;
		// 鑾峰彇鍖呯殑鍚嶅瓧 骞惰繘琛屾浛鎹�
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 瀹氫箟涓�釜鏋氫妇鐨勯泦鍚�骞惰繘琛屽惊鐜潵澶勭悊杩欎釜鐩綍涓嬬殑things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 寰幆杩唬涓嬪幓
			while (dirs.hasMoreElements()) {
				// 鑾峰彇涓嬩竴涓厓绱�
				URL url = dirs.nextElement();
				// 寰楀埌鍗忚鐨勫悕绉�
				String protocol = url.getProtocol();
				// 濡傛灉鏄互鏂囦欢鐨勫舰寮忎繚瀛樺湪鏈嶅姟鍣ㄤ笂
				if ("file".equals(protocol)) {
					System.out.println("file绫诲瀷鐨勬壂鎻�------------------");
					// 鑾峰彇鍖呯殑鐗╃悊璺緞
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 浠ユ枃浠剁殑鏂瑰紡鎵弿鏁翠釜鍖呬笅鐨勬枃浠�骞舵坊鍔犲埌闆嗗悎涓�
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 濡傛灉鏄痡ar鍖呮枃浠�
					// 瀹氫箟涓�釜JarFile
					System.out.println("jar绫诲瀷鐨勬壂鎻�---------------------");
					JarFile jar;
					try {
						// 鑾峰彇jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 浠庢jar鍖�寰楀埌涓�釜鏋氫妇绫�
						Enumeration<JarEntry> entries = jar.entries();
						// 鍚屾牱鐨勮繘琛屽惊鐜凯浠�
						while (entries.hasMoreElements()) {
							// 鑾峰彇jar閲岀殑涓�釜瀹炰綋 鍙互鏄洰褰�鍜屼竴浜沯ar鍖呴噷鐨勫叾浠栨枃浠�濡侻ETA-INF绛夋枃浠�
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 濡傛灉鏄互/寮�ご鐨�
							if (name.charAt(0) == '/') {
								// 鑾峰彇鍚庨潰鐨勫瓧绗︿覆
								name = name.substring(1);
							}
							// 濡傛灉鍓嶅崐閮ㄥ垎鍜屽畾涔夌殑鍖呭悕鐩稿悓
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 濡傛灉浠�/"缁撳熬 鏄竴涓寘
								if (idx != -1) {
									// 鑾峰彇鍖呭悕 鎶�/"鏇挎崲鎴�."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 濡傛灉鍙互杩唬涓嬪幓 骞朵笖鏄竴涓寘
								if ((idx != -1) || recursive) {
									// 濡傛灉鏄竴涓�class鏂囦欢 鑰屼笖涓嶆槸鐩綍
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 鍘绘帀鍚庨潰鐨�.class" 鑾峰彇鐪熸鐨勭被鍚�
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											// 娣诲姞鍒癱lasses
											addClass(classes, packageName + '.'
													+ className);

										} catch (ClassNotFoundException e) {
											// log
											// .error("娣诲姞鐢ㄦ埛鑷畾涔夎鍥剧被閿欒 鎵句笉鍒版绫荤殑.class鏂囦欢");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("鍦ㄦ壂鎻忕敤鎴峰畾涔夎鍥炬椂浠巎ar鍖呰幏鍙栨枃浠跺嚭閿�);
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	private static void addClass(Set<Class<?>> classes, String className)
			throws ClassNotFoundException {
		classes.add(Thread.currentThread().getContextClassLoader()
				.loadClass(className));
	}

	/**
	 * 浠ユ枃浠剁殑褰㈠紡鏉ヨ幏鍙栧寘涓嬬殑鎵�湁Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 鑾峰彇姝ゅ寘鐨勭洰褰�寤虹珛涓�釜File
		File dir = new File(packagePath);
		// 濡傛灉涓嶅瓨鍦ㄦ垨鑰�涔熶笉鏄洰褰曞氨鐩存帴杩斿洖
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("鐢ㄦ埛瀹氫箟鍖呭悕 " + packageName + " 涓嬫病鏈変换浣曟枃浠�);
			return;
		}
		// 濡傛灉瀛樺湪 灏辫幏鍙栧寘涓嬬殑鎵�湁鏂囦欢 鍖呮嫭鐩綍
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 鑷畾涔夎繃婊よ鍒�濡傛灉鍙互寰幆(鍖呭惈瀛愮洰褰� 鎴栧垯鏄互.class缁撳熬鐨勬枃浠�缂栬瘧濂界殑java绫绘枃浠�
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 寰幆鎵�湁鏂囦欢
		for (File file : dirfiles) {
			// 濡傛灉鏄洰褰�鍒欑户缁壂鎻�
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 濡傛灉鏄痡ava绫绘枃浠�鍘绘帀鍚庨潰鐨�class 鍙暀涓嬬被鍚�
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					addClass(classes, packageName + '.' + className);
					// 娣诲姞鍒伴泦鍚堜腑鍘�
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 缁忚繃鍥炲鍚屽鐨勬彁閱掞紝杩欓噷鐢╢orName鏈変竴浜涗笉濂斤紝浼氳Е鍙憇tatic鏂规硶锛屾病鏈変娇鐢╟lassLoader鐨刲oad骞插噣
					// classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName
					// + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("娣诲姞鐢ㄦ埛鑷畾涔夎鍥剧被閿欒 鎵句笉鍒版绫荤殑.class鏂囦欢");
					e.printStackTrace();
				}
			}
		}
	}
}

