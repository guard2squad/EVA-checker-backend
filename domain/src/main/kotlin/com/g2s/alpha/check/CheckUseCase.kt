package com.g2s.alpha.check

import com.fasterxml.jackson.module.kotlin.readValue
import com.g2s.alpha.common.ObjectMapperProvider
import com.g2s.alpha.enums.CheckMainItemStatus
import com.g2s.alpha.enums.CheckStatus
import com.g2s.alpha.enums.CheckSubItemStatus
import com.g2s.alpha.exceptions.CheckNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.io.FileNotFoundException
import java.io.IOException
import java.time.LocalDate

private const val MAIN_ITEMS_JSON_PATH = "/check_main_items.json"

@Component
class CheckUseCase(
    private val checkRepository: CheckRepository
) {
    fun createCheck(checkPeriod: CheckPeriod, zonecode: String, buildingNumber: String, unitNumber: String): String {
        val check = initializeCheck(checkPeriod, zonecode, buildingNumber, unitNumber)
        return check.checkId
    }

    private fun initializeCheck(
        checkPeriod: CheckPeriod,
        zonecode: String,
        buildingNumber: String,
        unitNumber: String
    ): Check {
        val mainItemDataList = loadMainItemsFromJson()

        val mainItems = mainItemDataList.map { mainItemData ->
            val subItems = mainItemData.subItems.map { subItemData ->
                SubItem(
                    name = subItemData.name,
                    description = subItemData.description,
                    videoUrl = subItemData.videoUrl,
                    status = CheckSubItemStatus.NOT_CHECKED,
                )
            }
            MainItem(
                name = mainItemData.name,
                subItems = subItems,
                status = CheckMainItemStatus.NOT_CHECKED,
            )
        }

        val check = Check(
            mainItems = mainItems,
            status = CheckStatus.NOT_CHECKED,
            checkPeriod = checkPeriod,
            checkCompletePeriod = LocalDate.now().toString(),
            zonecode = zonecode,
            buildingNumber = buildingNumber,
            unitNumber = unitNumber,
        )

        checkRepository.save(check)

        return check
    }

    @Throws(FileNotFoundException::class)
    private fun loadMainItemsFromJson(): List<MainItemData> {
        val objectMapper = ObjectMapperProvider.get()
        val inputStream = this::class.java.getResourceAsStream(MAIN_ITEMS_JSON_PATH)
            ?: throw FileNotFoundException("Resource not found: $MAIN_ITEMS_JSON_PATH")
        return try {
            objectMapper.readValue(inputStream)
        } catch (e: IOException) {
            throw RuntimeException("Failed to parse main_items.json", e)
        }
    }

    private data class MainItemData(
        val name: String,
        val subItems: List<SubItemData>,
    )

    private data class SubItemData(
        val name: String,
        val videoUrl: String,
        val description: String,
    )

    fun findCheck(checkId: String): Check {
        return checkRepository.findByCheckId(checkId)
            ?: throw CheckNotFoundException(checkId)
    }

    fun getChecks(
        zonecode: String,
        buildingNumber: String?,
        unitNumber: String?,
        checkPeriod: CheckPeriod?,
        pageable: Pageable,
    ): Page<Check> {
        val checks = checkRepository.findChecks(
            zonecode = zonecode,
            buildingNumber = buildingNumber,
            unitNumber = unitNumber,
            checkPeriod = checkPeriod,
            pageable = pageable
        )
        return checks
    }

    fun updateCheck(
        checkId: String,
        mainItems: List<MainItem>,
        status: CheckStatus,
        checkCompletePeriod: String,
        checkerId: String,
        checkerName: String,
        checkerContact: String
    ): Check {
        val old = checkRepository.findByCheckId(checkId)
            ?: throw CheckNotFoundException(checkId)
        val updated = old.copy(
            mainItems = mainItems,
            status = status,
            checkCompletePeriod = checkCompletePeriod,
            checkerId = checkerId,
            checkerName = checkerName,
            checkerContact = checkerContact,
        )

        return checkRepository.update(updated)
            ?: throw CheckNotFoundException(checkId)
    }

    fun deleteCheck(checkId: String): Boolean {
        return checkRepository.deleteCheck(checkId)
    }
}
