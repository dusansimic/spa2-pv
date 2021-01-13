import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.svetovid.io.SvetovidReader;
import org.svetovid.io.SvetovidWriter;

public class TreeIO<T> {

    public TreeIO(Class<T> type) {
        this(type, null);
    }

    ////////////
    // Config //
    ////////////

    public static class Config {

        public final String nullSymbol;
        public final int separation;
        public final int length;

        public Config() {
        	this(null, 1, 7);
        }

		public Config(String nullSymbol, int separation, int length) {
			this.nullSymbol = nullSymbol;
			this.separation = separation;
			this.length = length;
		}

		public Config setNullSymbol(String nullSymbol) {
			return new Config(nullSymbol, separation, length);
		}

		public Config setSeparation(int separation) {
			return new Config(nullSymbol, separation, length);
		}
		
		public Config setLength(int length) {
			return new Config(nullSymbol, separation, length);
		}
    }

	protected Config config;

    public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	//////////////
	// Printing //
	//////////////

    public void print(SvetovidWriter out, T tree) {
    	Object root = getRoot(tree);
        StringBuilder builder = new StringBuilder();
        appendTree(builder, root, config);
        out.print(builder.toString());
    }

	protected void appendTree(StringBuilder builder, Object tree, Config config) {
        String[] buildingBlocks = generateBuildingBlocks(config);
        appendRight(builder, tree, config, buildingBlocks, true, buildingBlocks[5]);
        appendNode(builder, tree, config, buildingBlocks[4]);
        appendLeft(builder, tree, config, buildingBlocks, false, buildingBlocks[5]);
    }

    protected void appendNode(StringBuilder builder, Object tree, Config config, String prefix) {
        builder.append(prefix);
        if (tree == null) {
            builder.append(config.nullSymbol == null ? VERTICAL_SYMBOL : config.nullSymbol);
        } else {
            builder.append("(o)");
            Object element = getElement(tree);
            if (element != null) {
                builder.append(" ");
                builder.append(element.toString());
            }
        }
        builder.append("\n");
    }

	protected void appendRight(StringBuilder builder, Object tree, Config config, String[] buildingBlocks, boolean isRight, String prefix) {
        if (tree == null) {
            return;
        }
        Object subtree = getRight(tree);
        if ((config.nullSymbol != null) || (subtree != null)) {
            appendSubtree(builder, subtree, config, buildingBlocks, true, prefix);
            for (int i = 0; i < config.separation; i++) {
                appendEmpty(builder, buildingBlocks, prefix);
            }
        }
    }

	protected void appendLeft(StringBuilder builder, Object tree, Config config, String[] buildingBlocks, boolean isRight, String prefix) {
        if (tree == null) {
            return;
        }
        Object subtree = getLeft(tree);
        if ((config.nullSymbol != null) || (subtree != null)) {
            for (int i = 0; i < config.separation; i++) {
                appendEmpty(builder, buildingBlocks, prefix);
            }
            appendSubtree(builder, subtree, config, buildingBlocks, false, prefix);
        }
    }

	protected void appendEmpty(StringBuilder builder, String[] buildingBlocks, String prefix) {
        builder.append(prefix);
        builder.append(buildingBlocks[2]);
        builder.append("\n");
    }

    protected void appendSubtree(StringBuilder builder, Object tree, Config config, String[] buildingBlocks, boolean isRight, String prefix) {
        String myPrefix = prefix;
        if (isRight == true) {
        	myPrefix = myPrefix + buildingBlocks[1];
        }
        if (isRight == false) {
        	myPrefix = myPrefix + buildingBlocks[3];
        }
        String noviPrefix = prefix + (!isRight ? buildingBlocks[2] : buildingBlocks[0]);
        appendRight(builder, tree, config, buildingBlocks, isRight, noviPrefix);
        appendNode(builder, tree, config, myPrefix);
        noviPrefix = prefix + (isRight ? buildingBlocks[2] : buildingBlocks[0]);
        appendLeft(builder, tree, config, buildingBlocks, isRight, noviPrefix);
    }

    protected static final String EMPTY_SYMBOL = " ";
    protected static final String RIGHT_SYMBOL = "/";
    protected static final String VERTICAL_SYMBOL = "|";
    protected static final String LEFT_SYMBOL = "\\";
    protected static final String HORIZONTAL_SYMBOL = "-";

    protected String[] generateBuildingBlocks(Config config) {
        String[] blocks = new String[6];
        blocks[0] = generateBlock(EMPTY_SYMBOL, EMPTY_SYMBOL, EMPTY_SYMBOL, config.length - 2);
        blocks[1] = generateBlock(EMPTY_SYMBOL, RIGHT_SYMBOL, HORIZONTAL_SYMBOL, config.length - 2);
        blocks[2] = generateBlock(EMPTY_SYMBOL, VERTICAL_SYMBOL, EMPTY_SYMBOL, config.length - 2);
        blocks[3] = generateBlock(EMPTY_SYMBOL, LEFT_SYMBOL, HORIZONTAL_SYMBOL, config.length - 2);
        blocks[4] = HORIZONTAL_SYMBOL;
        blocks[5] = EMPTY_SYMBOL;
        return blocks;
    }

    protected String generateBlock(String emptySymbol, String startSymbol, String repeatSymbol, int repeatCount) {
        StringBuilder builder = new StringBuilder();
        builder.append(emptySymbol);
        builder.append(startSymbol);
        for (int i = 0; i < repeatCount; i++) {
            builder.append(repeatSymbol);
        }
        return builder.toString();
    }

	/////////////
	// Reading //
	/////////////

	public T read(SvetovidReader in) {
		return newTree(parseTree(in, config)); 
    }

	protected Object parseTree(SvetovidReader in, Config config) {
		List<Object> elements = new ArrayList<>();
		List<Integer> levels = new ArrayList<>();
		Pattern levelPattern = Pattern.compile("[\\Q" + LEFT_SYMBOL + HORIZONTAL_SYMBOL + RIGHT_SYMBOL + "\\E]");
		String line = in.readLine();
		while ((line != null) && !line.isEmpty()) {
			Matcher matcher = levelPattern.matcher(line);
			int level = -1;
			if (matcher.find()) {
				level = matcher.start();
			}
			if (level != -1 && (config.nullSymbol == null || !line.endsWith(config.nullSymbol))) {
				Object tree = parseTree(line);
				elements.add(tree);
				levels.add(level);
			}
			line = in.readLine();
		}
		Object tree = formTree(0, elements.size(), levels, elements);
		return tree;
	}

	protected Object parseTree(String line) {
		String value;
		int beginIndex = line.indexOf('(');
		int endIndex = line.indexOf(')');
		if ((beginIndex != -1) && (endIndex != -1) && (beginIndex < endIndex)) {
			value = line.substring(endIndex + 1);
			if (value.length() == 0) {
				value = null;
			} else {
				value = value.substring(1);
			}
		} else {
			throw new NumberFormatException(line);
		}
		Object element = null;
		if (value != null) {
			element = newElement(value);
		}
		Object tree = newNode(element);
		return tree;
	}

	protected Object formTree(int beginIndex, int endIndex, List<Integer> levels, List<Object> elements) {
		if (beginIndex >= endIndex) {
			return null;
		}
		int minIndex = beginIndex;
		int minLevel = levels.get(minIndex);
		for (int i = beginIndex + 1; i < endIndex; i++) {
			int level = levels.get(i);
			if (level < minLevel) {
				minLevel = level;
				minIndex = i;
			}
		}
		Object tree = elements.get(minIndex);
		Object left = formTree(minIndex + 1, endIndex, levels, elements);
		Object right = formTree(beginIndex, minIndex, levels, elements);
		setLeft(tree, left);
		setRight(tree, right);
		return tree;
	}

	///////////
	// Magic //
	///////////

    @SuppressWarnings("unchecked")
	public TreeIO(Class<T> type, Config config) {

    	// Tree type
    	if (type == null) {
    		throw new IllegalArgumentException("Prosledjena klasa je null");
    	}
    	this.treeType = type;
		if (Modifier.isAbstract(this.treeType.getModifiers())) {
    		throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " ne sme da bude apstraktna");
		}

    	// Node type
    	Class<?>[] declaredClasses = treeType.getDeclaredClasses();
    	if (declaredClasses.length == 0) {
    		throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " nema unutrasnju klasu koja predstavlja cvorove");
    	}
	Class<?> staticOne = null;
	boolean multiStatic = false;
	for (Class<?> cl: declaredClasses) {
	    if (Modifier.isStatic(cl.getModifiers())) {
		if (staticOne == null) {
		    staticOne = cl;
		} else {
		    multiStatic = true;
		}
	    }
	}
	if (staticOne == null) {
    		throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " nema staticku unutrasnju klasu koja predstavlja cvorove");
    	}
    	if (multiStatic) {
    		throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " ima vise unutrasnjih statickih klasa, a mora biti samo jedna");
    	}
		this.nodeType = staticOne;
		if (Modifier.isAbstract(this.nodeType.getModifiers())) {
    		throw new IllegalArgumentException("Klasa " + this.nodeType.getName() + " ne sme da bude apstraktna");
		}
		if (!Modifier.isStatic(this.nodeType.getModifiers())) {
    		throw new IllegalArgumentException("Klasa " + this.nodeType.getName() + " mora da bude staticka");
		}

		// Tree constructors
		Constructor<?>[] declaredConstructors = this.treeType.getDeclaredConstructors();
		Constructor<T> defaultTreeConstructor = null;
		Constructor<T> treeConstructor = null;
		for (Constructor<?> constructor : declaredConstructors) {
			boolean throwingExceptions = false;
			for (Class<?> exception : constructor.getExceptionTypes()) {
				if (!RuntimeException.class.isAssignableFrom(exception)) {
					throwingExceptions = true;
				}
			}
			Class<?>[] parameters = constructor.getParameterTypes();
			if (parameters.length == 0
					&& !throwingExceptions) {
				defaultTreeConstructor = (Constructor<T>) constructor;
			}
			if (parameters.length == 1
					&& parameters[0].isAssignableFrom(this.nodeType)
					&& !throwingExceptions) {
				treeConstructor = (Constructor<T>) constructor;
			}
		}
		if (defaultTreeConstructor == null && treeConstructor == null) {
			throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " nema "
					+ this.nodeType.getSimpleName() + "() ili "
					+ this.nodeType.getSimpleName() + "(" + this.nodeType.getName() + ") konstruktor");
		}
		this.defaultTreeConstructor = defaultTreeConstructor;
		if (this.defaultTreeConstructor != null) {
			this.defaultTreeConstructor.setAccessible(true);
		}
		this.treeConstructor = treeConstructor;
		if (this.treeConstructor != null) {
			this.treeConstructor.setAccessible(true);
		}

		// Tree root field
		Field[] declaredFields = this.treeType.getDeclaredFields();
    	Field rootField = null;
    	for (Field field : declaredFields) {
    		if (Modifier.isStatic(field.getModifiers())) {
        		continue;
    		}
    		if (field.getType().isAssignableFrom(this.nodeType)) {
    			if (rootField != null) {
    	    		throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " ima vise polja koji bi mogli predstavljati koren stabla");
    			}
    			rootField = field;
    		}
    	}
		if (rootField == null) {
    		throw new IllegalArgumentException("Klasa " + this.treeType.getName() + " nema polje za predstavljanje korena");
		}
		this.rootField = rootField;
		if (Modifier.isStatic(this.rootField.getModifiers())) {
    		throw new IllegalArgumentException("Polje " + this.treeType.getName() + "." + this.rootField.getName() + " ne sme da bude staticko");
		}
		this.rootField.setAccessible(true);

    	// Node fields
    	declaredFields = this.nodeType.getDeclaredFields();
    	if (declaredFields.length == 0) {
    		throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " nema deklarisanih polja");
    	}
    	Field elementField = null;
    	Field leftField = null;
    	Field rightField = null;
    	for (Field field : declaredFields) {
    		if (Modifier.isStatic(field.getModifiers())) {
        		continue;
    		}
    		String fieldName = field.getName();
    		if (fieldName.startsWith("e") || fieldName.startsWith("i") || fieldName.startsWith("o")) {
    			if (elementField != null) {
    	    		throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " ima vise polja za predstavljanje elementa");
    			}
    			elementField = field;
    		}
    		if (fieldName.startsWith("l")
    				&& field.getType().isAssignableFrom(this.nodeType)) {
    			if (leftField != null) {
    	    		throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " ima vise polja za predstavljanje levog podstabla");
    			}
    			leftField = field;
    		}
    		if (fieldName.startsWith("d") || fieldName.startsWith("r")
    				&& field.getType().isAssignableFrom(this.nodeType)) {
    			if (rightField != null) {
    	    		throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " ima vise polja za predstavljanje desnog podstabla");
    			}
    			rightField = field;
    		}
    	}
		if (elementField == null) {
    		throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " nema polje za predstavljanje elementa");
		}
		if (leftField == null) {
			throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " nema polje za predstavljanje levog podstabla");
		}
		if (rightField == null) {
			throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " nema polje za predstavljanje desnog podstabla");
		}
		this.elementField = elementField;
		if (Modifier.isStatic(this.elementField.getModifiers())) {
    		throw new IllegalArgumentException("Polje " + this.treeType.getName() + "." + this.elementField.getName() + " ne sme da bude staticko");
		}
		this.elementField.getModifiers();
		
		this.elementField.setAccessible(true);
		this.leftField = leftField;
		if (Modifier.isStatic(this.leftField.getModifiers())) {
    		throw new IllegalArgumentException("Polje " + this.treeType.getName() + "." + this.leftField.getName() + " ne sme da bude staticko");
		}
		this.leftField.setAccessible(true);
		this.rightField = rightField;
		if (Modifier.isStatic(this.rightField.getModifiers())) {
    		throw new IllegalArgumentException("Polje " + this.treeType.getName() + "." + this.rightField.getName() + " ne sme da bude staticko");
		}
		this.rightField.setAccessible(true);

		// Element type
		this.elementType = elementField.getType();

		// Node constructors
		declaredConstructors = this.nodeType.getDeclaredConstructors();
		Constructor<?> defaultNodeConstructor = null;
		Constructor<?> nodeConstructor = null;
		Constructor<?> nodeConstructor3 = null;
		for (Constructor<?> constructor : declaredConstructors) {
			boolean throwingExceptions = false;
			for (Class<?> exception : constructor.getExceptionTypes()) {
				if (!RuntimeException.class.isAssignableFrom(exception)) {
					throwingExceptions = true;
				}
			}
			Class<?>[] parameters = constructor.getParameterTypes();
			if (parameters.length == 0
					&& !throwingExceptions) {
				defaultNodeConstructor = constructor;
			}
			if (parameters.length == 1
					&& parameters[0].isAssignableFrom(this.elementType)
					&& !throwingExceptions) {
				nodeConstructor = constructor;
			}
			if (parameters.length == 3
					&& parameters[0].isAssignableFrom(this.elementType)
					&& parameters[1].isAssignableFrom(this.nodeType)
					&& parameters[2].isAssignableFrom(this.nodeType)
					&& !throwingExceptions) {
				nodeConstructor3 = constructor;
			}
		}
		if (defaultNodeConstructor == null && nodeConstructor == null && nodeConstructor3 == null) {
			throw new IllegalArgumentException("Unutrasnja klasa " + this.nodeType.getName() + " nema "
					+ this.nodeType.getSimpleName() + "() ili "
					+ this.nodeType.getSimpleName() + "(" + this.elementType.getName() + ") ili "
					+ this.nodeType.getSimpleName() + "(" + this.elementType.getName() + ", " + this.nodeType.getSimpleName() + ", " + this.nodeType.getSimpleName() + ") konstruktor");
		}
		this.defaultNodeConstructor = defaultNodeConstructor;
		if (this.defaultNodeConstructor != null) {
			this.defaultNodeConstructor.setAccessible(true);
		}
		this.nodeConstructor = nodeConstructor;
		if (this.nodeConstructor != null) {
			this.nodeConstructor.setAccessible(true);
		}
		this.nodeConstructor3 = nodeConstructor3;
		if (this.nodeConstructor3 != null) {
			this.nodeConstructor3.setAccessible(true);
		}

		// Element methods
		Method elementFactoryMethod = null;
		Method[] declaredMethods = this.elementType.getDeclaredMethods();
		for (Method method : declaredMethods) {
    		if (!Modifier.isStatic(method.getModifiers())) {
        		continue;
    		}
			boolean throwingExceptions = false;
			for (Class<?> exception : method.getExceptionTypes()) {
				if (!RuntimeException.class.isAssignableFrom(exception)) {
					throwingExceptions = true;
				}
			}
			String methodName = method.getName();
			boolean familiarName = methodName.equals("fromString")
					|| methodName.equals("valueOf")
					|| methodName.equals("parse" + this.elementType.getSimpleName());
			boolean goodParameters = method.getParameterTypes().length == 1 && (method.getParameterTypes()[0] == String.class || method.getParameterTypes()[0] == Object.class);
			if (familiarName
					&& goodParameters
					&& this.elementType.isAssignableFrom(method.getReturnType())
					&& !throwingExceptions) {
				if (elementFactoryMethod != null) {
    	    		throw new IllegalArgumentException("Klasa " + this.elementType.getName() + " ima vise "
    	    				+ this.elementType.getSimpleName() + " fromString(String), "
    	    				+ this.elementType.getSimpleName() + " valueOf(String) i "
    	    				+ this.elementType.getSimpleName() + " parse" + this.elementType.getSimpleName() + "(String) metoda");
				}
				elementFactoryMethod = method;
			}
		}
		if (elementFactoryMethod == null) {
    		throw new IllegalArgumentException("Klasa " + this.elementType.getName() + " nema "
    				+ this.elementType.getSimpleName() + " fromString(String), "
    				+ this.elementType.getSimpleName() + " valueOf(String) ili "
    				+ this.elementType.getSimpleName() + " parse" + this.elementType.getSimpleName() + "(String) metod");
		}
		this.elementFactoryMethod = elementFactoryMethod;
		this.elementFactoryMethod.setAccessible(true);

    	// Config
    	if (config == null) {
    		config = new Config();
    	}
    	this.config = config;

    }

	protected final Class<T> treeType;
    protected final Constructor<T> treeConstructor;
    protected final Constructor<T> defaultTreeConstructor;
    protected final Field rootField;
    protected final Class<?> nodeType;
    protected final Field elementField;
    protected final Field leftField;
    protected final Field rightField;
    protected final Constructor<?> nodeConstructor3;
    protected final Constructor<?> nodeConstructor;
    protected final Constructor<?> defaultNodeConstructor;
    protected final Class<?> elementType;
    protected final Method elementFactoryMethod;

    private Object getRoot(T tree) {
		try {
			Object value = rootField.get(tree);
			return value;
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		}
	}
    
    private void setRoot(T tree, Object root) {
		try {
			rootField.set(tree, root);
		} catch (IllegalAccessException e) {
			// Will never happen
		}
	}

    private Object getElement(Object node) {
		try {
			Object value = elementField.get(node);
			return value;
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		}
	}
    
    private void setElement(Object node, Object value) {
		try {
			elementField.set(node, value);
		} catch (IllegalAccessException e) {
			// Will never happen
		}
	}

    private Object getLeft(Object node) {
		try {
			Object value = leftField.get(node);
			return value;
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		}
	}

    private void setLeft(Object node, Object value) {
		try {
			leftField.set(node, value);
		} catch (IllegalAccessException e) {
			// Will never happen
		}
	}

    private Object getRight(Object node) {
		try {
			Object value = rightField.get(node);
			return value;
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		}
	}

    private void setRight(Object node, Object value) {
		try {
			rightField.set(node, value);
		} catch (IllegalAccessException e) {
			// Will never happen
		}
	}

	private Object newElement(String value) {
		try {
			return elementFactoryMethod.invoke(null, value);
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		} catch (InvocationTargetException e) {
			// Will not be a checked exception
			RuntimeException cause = (RuntimeException) e.getCause();
			throw cause;
		}
	}

	private Object newNode(Object element) {
		try {
			if (nodeConstructor != null) {
				return nodeConstructor.newInstance(element);
			}
			if (nodeConstructor3 != null) {
				return nodeConstructor3.newInstance(element, null, null);
			}
			Object node = defaultNodeConstructor.newInstance();
			setElement(node, element);
			return node;
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		} catch (InstantiationException e) {
			// Will never happen
			return null;
		} catch (InvocationTargetException e) {
			// Will not be a checked exception
			RuntimeException cause = (RuntimeException) e.getCause();
			throw cause;
		}
	}

	private T newTree(Object root) {
		try {
			if (treeConstructor != null) {
				return treeConstructor.newInstance(root);
			}
			T tree = defaultTreeConstructor.newInstance();
			setRoot(tree, root);
			return tree;
		} catch (IllegalAccessException e) {
			// Will never happen
			return null;
		} catch (InstantiationException e) {
			// Will never happen
			return null;
		} catch (InvocationTargetException e) {
			// Will not be a checked exception
			RuntimeException cause = (RuntimeException) e.getCause();
			throw cause;
		}
	}
}
