package com.example.ferreteriahogar.viewModels

import com.example.ferreteriahogar.data.Detalle_Hoja
import com.example.ferreteriahogar.data.Inventory
import com.example.ferreteriahogar.data.User
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class InventoryViewModelTest : StringSpec({

    lateinit var viewModel: InventoryViewModel

    beforeTest {
        viewModel = InventoryViewModel()
    }
    val fakeUser = User(
        id = 1,
        username = "Usuario Test",
        role = "ADMIN"
    )
    "selectInventory setea inventario y limpia detalles" {
        val inventory = Inventory(
            code = "INV01",
            name = "Inventario Principal",
            status = "ACTIVE",
            user = fakeUser,
            items = emptyList()
        )

        viewModel.detalles.add(Detalle_Hoja("P1", "Producto", 2))

        viewModel.selectInventory(inventory)

        viewModel.selectedInventory.value shouldBe inventory
        viewModel.detalles shouldHaveSize 0
    }

    "addProduct suma cantidades si el producto ya existe" {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 2))
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 3))

        viewModel.detallesTemp shouldHaveSize 1
        viewModel.detallesTemp[0].cantidad shouldBe 5
    }

    "addProduct agrega producto si no existe" {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 1))
        viewModel.addProduct(Detalle_Hoja("P2", "Clavos", 4))

        viewModel.detallesTemp shouldHaveSize 2
    }

    "updateCantidadTemp actualiza cantidad correctamente" {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 1))

        viewModel.updateCantidadTemp(0, 10)

        viewModel.detallesTemp[0].cantidad shouldBe 10
    }

    "removeTemp elimina producto temporal" {
        viewModel.addProduct(Detalle_Hoja("P1", "Martillo", 1))
        viewModel.addProduct(Detalle_Hoja("P2", "Clavos", 4))

        viewModel.removeTemp(0)

        viewModel.detallesTemp shouldHaveSize 1
        viewModel.detallesTemp[0].codigo shouldBe "P2"
    }
})