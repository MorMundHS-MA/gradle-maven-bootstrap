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
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.api.artifacts.repositories.MetadataSupplierAware.setMetadataSupplier].
 *
 * @see org.gradle.api.artifacts.repositories.MetadataSupplierAware.setMetadataSupplier
 * @since 4.9
 */
inline fun org.gradle.api.artifacts.repositories.MetadataSupplierAware.`setMetadataSupplier`(`rule`: kotlin.reflect.KClass<out org.gradle.api.artifacts.ComponentMetadataSupplier>): Unit =
    `setMetadataSupplier`(`rule`.java)


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.api.artifacts.repositories.MetadataSupplierAware.setMetadataSupplier].
 *
 * @see org.gradle.api.artifacts.repositories.MetadataSupplierAware.setMetadataSupplier
 * @since 4.9
 */
inline fun org.gradle.api.artifacts.repositories.MetadataSupplierAware.`setMetadataSupplier`(`rule`: kotlin.reflect.KClass<out org.gradle.api.artifacts.ComponentMetadataSupplier>, `configureAction`: org.gradle.api.Action<in org.gradle.api.ActionConfiguration>): Unit =
    `setMetadataSupplier`(`rule`.java, `configureAction`)


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.api.artifacts.repositories.MetadataSupplierAware.setComponentVersionsLister].
 *
 * @see org.gradle.api.artifacts.repositories.MetadataSupplierAware.setComponentVersionsLister
 * @since 4.9
 */
inline fun org.gradle.api.artifacts.repositories.MetadataSupplierAware.`setComponentVersionsLister`(`lister`: kotlin.reflect.KClass<out org.gradle.api.artifacts.ComponentMetadataVersionLister>): Unit =
    `setComponentVersionsLister`(`lister`.java)


/**
 * Kotlin extension function taking [kotlin.reflect.KClass] for [org.gradle.api.artifacts.repositories.MetadataSupplierAware.setComponentVersionsLister].
 *
 * @see org.gradle.api.artifacts.repositories.MetadataSupplierAware.setComponentVersionsLister
 * @since 4.9
 */
inline fun org.gradle.api.artifacts.repositories.MetadataSupplierAware.`setComponentVersionsLister`(`lister`: kotlin.reflect.KClass<out org.gradle.api.artifacts.ComponentMetadataVersionLister>, `configureAction`: org.gradle.api.Action<in org.gradle.api.ActionConfiguration>): Unit =
    `setComponentVersionsLister`(`lister`.java, `configureAction`)

