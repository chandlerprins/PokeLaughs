package vcmsa.projects.pokelaughs

data class Pokemon(
    val name: String,
    val sprites: Sprites,
    val types: List<TypeSlot>
)

data class Sprites(val front_default: String)

data class TypeSlot(val type: TypeName)

data class TypeName(val name: String)