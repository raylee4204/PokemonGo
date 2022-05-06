package city.samaritan.pokemongo.model

data class Pokemon(
    val id: Int,
    val name: String,
    val defaultFrontImage: String,
    val stats: List<Stat>
) {
    data class Stat(val name: String, val base: Int)
}