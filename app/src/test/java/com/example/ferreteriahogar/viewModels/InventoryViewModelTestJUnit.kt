package com.example.ferreteriahogar.viewModels

import com.example.ferreteriahogar.data.Detalle_Hoja
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.data.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InventoryViewModelTestJUnit {

    private lateinit var viewModel: InventoryViewModel

    private val fakeUser = User(
        id = 1,
        username = "Usuario Test",
        role = "ADMIN"
    )

    @BeforeEach
    fun setup() {
        viewModel = InventoryViewModel()
    }

    @Test
    fun `selectInventory setea inventario y limpia detalles`() {
        val inventory = Inventory(
            code = "INV01",
            name = "Inventario Principal",
            status = "ACTIVE",
            user = fakeUser,
            items = emptyList()
        )

        viewModel.detalles.add(Detalle_Hoja("P1", "Producto", 2))

        viewModel.selectInventory(inventory)

        assertEquals(inventory, viewModel.selectedInventory.value)
        assertEquals(0, viewModel.detalles.size)
    }

    @Test
    fun `addProduct suma cantidades si el producto ya existe`() {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 2))
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 3))

        assertEquals(1, viewModel.detallesTemp.size)
        assertEquals(5, viewModel.detallesTemp[0].cantidad)
    }

    @Test
    fun `addProduct agrega producto si no existe`() {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 1))
        viewModel.addProduct(Detalle_Hoja("P2", "Clavos", 4))

        assertEquals(2, viewModel.detallesTemp.size)
    }

    @Test
    fun `updateCantidadTemp actualiza cantidad correctamente`() {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 1))

        viewModel.updateCantidadTemp(0, 10)

        assertEquals(10, viewModel.detallesTemp[0].cantidad)
    }

    @Test
    fun `removeTemp elimina producto temporal`() {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 1))
        viewModel.addProduct(Detalle_Hoja("P2", "Clavos", 4))

        viewModel.removeTemp(0)

        assertEquals(1, viewModel.detallesTemp.size)
        assertEquals("P2", viewModel.detallesTemp[0].codigo)
    }
}