package ru.DmN.pht.demo

import ru.DmN.siberia.Console
import ru.DmN.siberia.ConsoleOld.compileModule
import ru.DmN.siberia.ConsoleOld.initCompileAndRunModule
import ru.DmN.siberia.ConsoleOld.initHelp
import java.io.File
import java.net.URLClassLoader

object Console : Console() {
    @JvmStatic
    fun initProgramInfo() {
        this.actions.add(Triple("О программе", "Выводит информацию о программе.", Runnable {
            println("""
                Проект: Демонстрационная Консоль Пихты
                Версия: 1.0.0
                Авторы: DomamaN202
            """.trimIndent())
        }))
    }

    @JvmStatic
    fun Console.initCompileAndRunExamples() {
        this.actions.add(Triple("Сборка & Запуск Примеров", "Компилирует и запускает пример.", Runnable {
            val examples = getExamples()
            println("Примеры:")
            examples.forEachIndexed { i, it -> println("$i. $it") }
            print("Выбирите пример: ")
            if (!compileModule(examples[readln().toInt()])) {
                println("Модуль не был собран успешно.")
                return@Runnable
            }
            println("Модуль успешно собран.\n")
            println(Class.forName("App", true, URLClassLoader(arrayOf(File("dump").toURL()))).getMethod("main").invoke(null))
        }))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        initHelp()
        initProgramInfo()
        initCompileAndRunModule()
        initCompileAndRunExamples()
        run()
    }

    @JvmStatic
    fun getExamples(): List<String> {
        val list = ArrayList<String>()
        getExamples(list, "examples")
        return list
    }

    private fun getExamples(list: MutableList<String>, dir: String) {
        if (File(dir, "module.pht").exists())
            list += dir
        else File(dir).listFiles { it -> it.isDirectory }?.forEach { getExamples(list, "$dir/${it.name}") }
    }
}