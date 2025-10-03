# Мутационное тестирование

## Что такое мутационные тесты?

Мутационное тестирование (Mutation Testing) — это техника оценки качества тестов, которая работает следующим образом:

1. **Создание мутантов** — в исходный код вносятся небольшие изменения (мутации)
2. **Запуск тестов** — существующие тесты запускаются против мутированного кода
3. **Анализ результатов**:
   - Если тест **падает** — мутант "убит" (killed) ✅ — тест качественный
   - Если тест **проходит** — мутант "выжил" (survived) ❌ — тест недостаточно хорош

### Примеры мутаций

```java
// Оригинальный код
if (password.length() > 8) {
    return true;
}

// Мутация 1: Изменение оператора сравнения
if (password.length() >= 8) {  // > заменено на >=
    return true;
}

// Мутация 2: Изменение константы
if (password.length() > 9) {  // 8 заменено на 9
    return true;
}

// Мутация 3: Инверсия условия
if (password.length() <= 8) {  // > заменено на <=
    return true;
}
```

Если ваши тесты не обнаруживают эти изменения — значит они неполные.

## Зачем нужны мутационные тесты?

### Проблемы, которые решают мутационные тесты:

1. **Высокое покрытие ≠ Качественные тесты**
   ```java
   // Покрытие 100%, но тест бесполезен
   @Test
   public void testEncrypt() {
       String result = encryptor.encrypt("password");
       assertNotNull(result); // Слабая проверка!
   }
   ```

2. **Обнаружение мёртвого кода**
   - Если мутация в коде не убивает ни один тест — возможно, код не нужен

3. **Проверка граничных условий**
   - Выявляет, тестируете ли вы `>` vs `>=`, `==` vs `!=` и т.д.

4. **Качество assertion'ов**
   - Показывает, насколько строго проверяются результаты

### Метрики качества:

- **Mutation Score** = (Убитые мутанты / Всего мутантов) × 100%
- Хороший результат: **70-80%+**
- Отличный результат: **90%+**

## Внедрение в данный проект

Ваш проект — это Password Manager на Kotlin с использованием Gradle. Рекомендуемый инструмент — **PITest**.

### Шаг 1: Добавление PITest в проект

Откройте `build.gradle.kts` и добавьте плагин:

```kotlin
plugins {
    kotlin("jvm") version "1.9.0"
    id("info.solidsoft.pitest") version "1.15.0"
}

dependencies {
    // Ваши существующие зависимости
    testImplementation(kotlin("test"))
}

// Конфигурация PITest
pitest {
    // Укажите пакеты для мутационного тестирования
    targetClasses.set(listOf("com.passwordmanager.*"))

    // Укажите пакеты с тестами
    targetTests.set(listOf("com.passwordmanager.*"))

    // Форматы отчётов
    outputFormats.set(listOf("HTML", "XML"))

    // Не создавать отчёты с временными метками
    timestampedReports.set(false)

    // Количество потоков
    threads.set(Runtime.getRuntime().availableProcessors())

    // Мутаторы (можно настроить)
    mutators.set(listOf("DEFAULTS"))

    // Verbose режим
    verbose.set(true)

    // Таймаут для тестов (в миллисекундах)
    timeoutConstInMillis.set(10000)

    // Минимальный порог покрытия мутациями
    mutationThreshold.set(75)
    coverageThreshold.set(80)
}
```

### Шаг 2: Запуск мутационного тестирования

```bash
# Запуск всех мутационных тестов
./gradlew pitest

# Только для Windows
gradlew.bat pitest

# С детальным выводом
./gradlew pitest --info
```

### Шаг 3: Анализ результатов

После выполнения отчёты появятся в:
```
build/reports/pitest/index.html
```

Откройте `index.html` в браузере для просмотра:
- Общий mutation score
- Детализация по классам
- Список выживших мутантов
- Какие строки кода недостаточно протестированы

### Шаг 4: Интерпретация результатов

#### Пример отчёта:
```
>> Line Coverage: 85%
>> Mutation Coverage: 62%
```

**Что делать с выжившими мутантами:**

1. **Survived Mutants** — добавьте тесты:
   ```
   Class: SecureStorage
   Line 45: negated conditional → SURVIVED
   ```
   Значит нужен тест, проверяющий оба результата условия.

2. **No Coverage** — код вообще не тестируется

3. **Timeout** — тест слишком долгий, возможно бесконечный цикл

## Применение для Password Manager

### Критичные области для мутационного тестирования:

#### 1. **Криптография (SecureStorage, EnhancedStorage)**
```kotlin
// Пример: проверка правильности шифрования/дешифрования
@Test
fun testEncryptionDecryption() {
    val original = "MyPassword123"
    val encrypted = secureStorage.encrypt(original)
    val decrypted = secureStorage.decrypt(encrypted)

    // Хороший тест
    assertEquals(original, decrypted)
    assertNotEquals(original, encrypted) // Проверяем, что шифрование работает
    assertTrue(encrypted.length > 0)
}
```

Мутационные тесты выявят:
- Правильно ли работает инверсия данных
- Проверяете ли вы пустые строки
- Тестируете ли вы null-случаи

#### 2. **Генерация паролей (PasswordGenerator)**
```kotlin
@Test
fun testPasswordLength() {
    val password = generator.generate(length = 16)

    // Плохой тест - мутант выживет
    assertTrue(password.length > 0)

    // Хороший тест - убьёт мутантов
    assertEquals(16, password.length)
    assertTrue(password.any { it.isUpperCase() })
    assertTrue(password.any { it.isLowerCase() })
    assertTrue(password.any { it.isDigit() })
}
```

#### 3. **Валидация паролей**
```kotlin
@Test
fun testPasswordValidation() {
    // Граничные условия
    assertFalse(validator.isValid("1234567"))  // < 8 символов
    assertTrue(validator.isValid("12345678"))   // = 8 символов
    assertTrue(validator.isValid("123456789"))  // > 8 символов
}
```

### Рекомендуемая стратегия:

1. **Начните с малого:**
   ```kotlin
   pitest {
       targetClasses.set(listOf("com.passwordmanager.PasswordGenerator"))
   }
   ```

2. **Постепенно добавляйте классы:**
   - SecureStorage
   - EnhancedStorage
   - Validation логика

3. **Установите цели:**
   - Начальная цель: 70% mutation score
   - Конечная цель: 85%+ mutation score

## Настройка мутаторов

PITest поддерживает различные типы мутаций:

```kotlin
pitest {
    mutators.set(listOf(
        "CONDITIONALS_BOUNDARY",  // > в >=, < в <=
        "INCREMENTS",             // i++ в i--
        "INVERT_NEGS",            // -x в x
        "MATH",                   // + в -, * в /
        "NEGATE_CONDITIONALS",    // == в !=, > в <=
        "RETURN_VALS",            // true в false
        "VOID_METHOD_CALLS"       // удаление вызовов void методов
    ))
}

// Или использовать предустановки:
mutators.set(listOf("STRONGER"))  // Более агрессивные мутации
mutators.set(listOf("DEFAULTS"))  // Стандартный набор
```

## Интеграция с CI/CD

### GitHub Actions пример:

```yaml
name: Mutation Testing

on: [push, pull_request]

jobs:
  pitest:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Run PITest
        run: ./gradlew pitest
      - name: Upload report
        uses: actions/upload-artifact@v3
        with:
          name: pitest-report
          path: build/reports/pitest/
      - name: Check mutation threshold
        run: ./gradlew pitest
```

## Частые вопросы

### Q: Как долго выполняются мутационные тесты?
A: Дольше обычных тестов в 5-10 раз. Для проекта с 100 тестами — около 5-15 минут.

### Q: Нужно ли стремиться к 100% mutation score?
A: Нет. 80-90% — отличный результат. 100% часто непрактично.

### Q: Что делать с эквивалентными мутантами?
A: Некоторые мутанты функционально идентичны оригиналу. PITest позволяет их исключать.

### Q: Сколько стоит внедрение?
A: PITest бесплатен и open-source.

## Дополнительные ресурсы

- [PITest официальная документация](https://pitest.org/)
- [PITest для Gradle](https://gradle-pitest-plugin.solidsoft.info/)
- [Mutation Testing Best Practices](https://pitest.org/quickstart/mutators/)

## Следующие шаги для этого проекта

1. ✅ Добавить PITest в `build.gradle.kts`
2. ✅ Запустить первый раз: `./gradlew pitest`
3. ✅ Проанализировать результаты
4. ✅ Улучшить тесты для критичных классов (SecureStorage, PasswordGenerator)
5. ✅ Настроить минимальный порог в CI/CD
6. ✅ Регулярно запускать при изменениях

---

**Мутационное тестирование — это тест для ваших тестов!**
