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

package org.gradle.kotlin.dsl


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.caching.configuration.BuildCacheConfiguration.registerBuildCacheService].
 *
 * @see org.gradle.caching.configuration.BuildCacheConfiguration.registerBuildCacheService
 * @since 3.5
 */
inline fun <T : org.gradle.caching.configuration.BuildCache> org.gradle.caching.configuration.BuildCacheConfiguration.`registerBuildCacheService`(`configurationType`: kotlin.reflect.KClass<T>, `buildCacheServiceFactoryType`: kotlin.reflect.KClass<out org.gradle.caching.BuildCacheServiceFactory<in T>>): Unit =
    `registerBuildCacheService`(`configurationType`.java, `buildCacheServiceFactoryType`.java)


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.caching.configuration.BuildCacheConfiguration.remote].
 *
 * @see org.gradle.caching.configuration.BuildCacheConfiguration.remote
 * @since 3.5
 */
inline fun <T : org.gradle.caching.configuration.BuildCache> org.gradle.caching.configuration.BuildCacheConfiguration.`remote`(`type`: kotlin.reflect.KClass<T>): T =
    `remote`(`type`.java)


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.caching.configuration.BuildCacheConfiguration.remote].
 *
 * @see org.gradle.caching.configuration.BuildCacheConfiguration.remote
 * @since 3.5
 */
inline fun <T : org.gradle.caching.configuration.BuildCache> org.gradle.caching.configuration.BuildCacheConfiguration.`remote`(`type`: kotlin.reflect.KClass<T>, `configuration`: org.gradle.api.Action<in T>): T =
    `remote`(`type`.java, `configuration`)

