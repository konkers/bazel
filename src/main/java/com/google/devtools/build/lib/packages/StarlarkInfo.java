// Copyright 2023 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.packages;

import java.util.Map;
import javax.annotation.Nullable;
import net.starlark.java.eval.HasBinary;
import net.starlark.java.syntax.Location;

/** Superclass (provider instance) for providers defined in Starlark. */
public abstract class StarlarkInfo extends StructImpl implements HasBinary {

  StarlarkInfo(@Nullable Location location) {
    super(location);
  }

  /**
   * Creates a schemaless provider instance with the given provider type and field values.
   *
   * @param provider A {@code Provider} without a schema. {@code StarlarkProvider} with a schema is
   *     not supported by this call.
   * @param values the field values
   * @param loc the creation location for this instance. Built-in provider instances may use {@link
   *     Location#BUILTIN}, which is the default if null.
   */
  public static StarlarkInfo create(
      Provider provider, Map<String, Object> values, @Nullable Location loc) {
    return StarlarkInfoNoSchema.createSchemaless(provider, values, loc);
  }

  // Relax visibility to public. getValue() is widely used to directly access fields from native
  // rule logic. Compared to Starlark.getattr(), it also avoids the need for the caller to pass a
  // Semantics.
  @Nullable
  @Override
  public abstract Object getValue(String name);
}
