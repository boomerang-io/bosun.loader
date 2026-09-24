package net.boomerangplatform.migration;

import io.mongock.runner.core.executor.MongockRunner;

public interface BoomerangMigration {
  public MongockRunner mongock();
}
