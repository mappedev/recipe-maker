val recipes: MutableMap<String, List<String>> = mutableMapOf()

fun main() {
  println(
    """
    
      :: Bienvenido a Recipe Maker ::
      -------------------------------
    """.trimIndent()
  )
  spacesLine()

  do {
    menu()
    val option: String? = selectOption("Opción")
    spacesLine()

    when (option) {
        "1" -> makeRecipe()
        "2" -> viewRecipes()
        "3" -> println("¡Que quede sabroso!")
        else -> invalidOption()
    }
  } while (option != "3")
}

// Lambdas
val selectOption: (String) -> String? = { title ->
  print("${title}: ")
  readLine()?.ifBlank { null }
}

// Functions
fun spacesLine(n: Int = 1) {
  for (i in 1..n) {
    println("\n")
  }
}

fun invalidOption() {
  println("Por favor seleccione una opción válida.")
  spacesLine()
}

fun invalidName() {
  println("Por favor ingresa un nombre.")
  spacesLine()
}

fun menu() {
  println(
    """
      Selecciona la opción deseada:
      1.Hacer una receta.
      2.Ver mis recetas.
      3.Salir.
    """.trimIndent()
  )
}

fun makeRecipe() {
  val ingredients: MutableList<String> = mutableListOf(
    "Agua",
    "Leche",
    "Carne",
    "Verduras",
    "Frutas",
    "Cereal",
    "Huevos",
    "Aceites"
  )
  val selectedIngredients: MutableList<String> = mutableListOf()
  var recipeName: String = ""
  
  fun addIngredientToRecipe(ingredientIndex: Int) {
    selectedIngredients.add(ingredients[ingredientIndex])
    println("A tu receta $recipeName se le ha agregado ${ingredients[ingredientIndex]}")
    spacesLine()
  }

  fun addIngredient() {
    do {
      println("¿Qué nombre tiene este ingrediente?")
      val ingredientName: String? = selectOption("Nombre")
      spacesLine()

      if (ingredientName == null) {
        invalidName()
      } else {
        ingredients.add(ingredientName)
        println("Ingrediente agregado a la lista de ingredientes.")
        spacesLine()
        break
      }
    } while(true)
  }

  fun addRecipeAndResetList() {
    if (selectedIngredients.isNotEmpty()) {
      recipes[recipeName] = selectedIngredients.distinct()
      selectedIngredients.clear()
    }

    println("Volviendo...")
    spacesLine()
  }

  do {
    println("¿Que nombre tendrá tu receta")
    val recipeNameOption: String? = selectOption("Nombre")
    spacesLine()

    if (recipeNameOption == null) {
      invalidName()
    } else if (recipes.containsKey(recipeNameOption)) {
      println("Esa receta ya fue creada, crea una receta nueva.")
      spacesLine()
    } else {
      recipeName = recipeNameOption
      break
    }
  } while(true)

  do {
    println("¿Tu receta $recipeName que ingredientes tendrá?")
    ingredients.forEachIndexed { index: Int, ingredient: String ->
      println("${index+1}. $ingredient")
    }
    println("${ingredients.size+1}. Agregar ingrediente para esta receta")
    println("${ingredients.size+2}. Volver el menú")
    val option: Int = selectOption("Opción")?.toInt() ?: 0
    spacesLine()

    when (option) {
      in 1..ingredients.size -> addIngredientToRecipe(option-1)
      ingredients.size+1 -> addIngredient()
      ingredients.size+2 -> addRecipeAndResetList()
      else -> invalidOption()
    }
  } while (option != ingredients.size+2)
}

fun viewRecipes() {
  if (recipes.isNullOrEmpty()) {
    println("No ha creado una receta aún, ¡crea una!")
    spacesLine()
    return
  }

  recipes.forEach { key, values ->
    println("Receta: $key")
    println("Ingredientes:")
    values.forEach { value -> println(value)}
    spacesLine()
  }
}
