package com.maruchin.data.diet

import java.net.URL

data class Diet(
    val id: String,
    val name: String,
    val cover: URL,
    val groups: List<Group>,
)

val sampleDiets = listOf(
    Diet(
        id = "man_1",
        name = "Elastyczna dieta \"Standard 1.0\"",
        cover = URL("https://dailyeffect.pl/wp-content/uploads/2022/05/PRZEPISY-KLASYCZNE-SIMONTE-2-2.jpg"),
        groups = listOf(
            Group(
                name = "Grupa 1",
                macro = Macro(
                    calories = 500,
                    protein = 35,
                    fat = 10,
                    carbs = 70
                ),
                recipes = listOf(
                    Recipe(
                        name = "Kanapki z tuńczykiem i awokado",
                        macro = Macro(
                            calories = 500,
                            protein = 36,
                            fat = 10,
                            carbs = 67
                        ),
                        ingredients = listOf(
                            "Ulubione pieczywo 100g",
                            "Dojrzałe awokado 50g",
                            "Tuńczyk w sosie własnym 120g",
                            "Ulubione warzywa (pomidor, cebula, ogórek)",
                            "Ulubione przyprawy (sól, pieprz)"
                        ),
                        steps = listOf(
                            "Dojrzałe awokado rozgnieć widelcem z odrobiną soli i piperzu",
                            "Pozsmaruj pastą pieczywo i nałóż tuńczyka wraz z pomidorami"
                        ),
                        tips = listOf(
                            "Upewnij się że kupujesz tuńczyka w sosie własnym, a nie w oleju!. Tuńczyk w oleju zawiera dodatkowe kalorie, a dodany olej jest przeważnie słabej jakości."
                        )
                    ),
                    Recipe(
                        name = "Kanapki z szynką i serem",
                        macro = Macro(
                            calories = 498,
                            protein = 33,
                            fat = 12,
                            carbs = 63
                        ),
                        ingredients = listOf(
                            "Ulubione pieczywo 100g",
                            "Chuda szyka 50g",
                            "Ser żółty 50g",
                            "Ulubione warzywa (sałata, ogórek, pomidor)"
                        ),
                        steps = listOf(
                            "Nałóż na pieczywo wszystkie składniki"
                        ),
                        tips = listOf(
                            "Zamiast tradycyjnych kanapek możesz zrobić tosty. Wystarczy że włóżysz do opiekacza pieczywo bez warzyw, a warzywa podasz osobno w formie sałatki.",
                            "Kupując szynkę upewnij się że ma minimum 90% mięsa (najlepiej +100%) i nie więcej niż 3g tłuszczu na 100g produktu."
                        )
                    )
                )
            )
        )
    ),
    Diet(
        id = "man_2",
        name = "Elastyczna dieta \"Standard 2.0\"",
        cover = URL("https://dailyeffect.pl/wp-content/uploads/2022/12/PRODUKTY-OKLADKI-NA-STRONE-32-2.png"),
        groups = emptyList(),
    ),
    Diet(
        id = "man_3",
        name = "Elastyczna dieta \"Standard 3.0\"",
        cover = URL("https://dailyeffect.pl/wp-content/uploads/2022/10/JESIENNO-ZIMOWE-SIMONTE-4-2.jpg"),
        groups = emptyList(),
    )
)