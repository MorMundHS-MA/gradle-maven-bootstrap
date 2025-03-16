/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress(
    "unused",
    "nothing_to_inline",
    "useless_cast",
    "unchecked_cast",
    "extension_shadowed_by_member",
    "redundant_projection",
    "RemoveRedundantBackticks",
    "ObjectPropertyName",
    "deprecation",
    "detekt:all"
)
@file:org.gradle.api.Generated
@file:org.gradle.api.Incubating

package org.gradle.kotlin.dsl


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.api.initialization.SharedModelDefaults.add].
 *
 * @see org.gradle.api.initialization.SharedModelDefaults.add
 * @since 8.10
 */
@org.gradle.api.Incubating
inline fun <T : Any> org.gradle.api.initialization.SharedModelDefaults.`add`(`name`: String, `publicType`: kotlin.reflect.KClass<T>, `configureAction`: org.gradle.api.Action<in T>): Unit =
    `add`(`name`, `publicType`.java, `configureAction`)

