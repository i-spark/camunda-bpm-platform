/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.test.history.useroperationlog;

import org.camunda.bpm.engine.EntityTypes;
import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.test.Deployment;

/**
 * @author Thorben Lindhauer
 *
 */
public class UserOperationLogJobTest extends AbstractUserOperationLogTest {

  @Deployment(resources = {"org/camunda/bpm/engine/test/history/asyncTaskProcess.bpmn20.xml"})
  public void testSetJobPriority() {
    // given a job
    runtimeService.startProcessInstanceByKey("asyncTaskProcess");
    Job job = managementService.createJobQuery().singleResult();

    // when I set a job priority
    managementService.setJobPriority(job.getId(), 42);

    // then an op log entry is written
    UserOperationLogEntry userOperationLogEntry = historyService
            .createUserOperationLogQuery()
            .operationType(UserOperationLogEntry.OPERATION_TYPE_SET_PRIORITY)
            .singleResult();
    assertNotNull(userOperationLogEntry);

    assertEquals(EntityTypes.JOB, userOperationLogEntry.getEntityType());
    assertEquals(job.getId(), userOperationLogEntry.getJobId());

    assertEquals(UserOperationLogEntry.OPERATION_TYPE_SET_PRIORITY,
        userOperationLogEntry.getOperationType());

    assertEquals("priority", userOperationLogEntry.getProperty());
    assertEquals("42", userOperationLogEntry.getNewValue());
    assertEquals("0", userOperationLogEntry.getOrgValue());

    assertEquals(USER_ID, userOperationLogEntry.getUserId());

    assertEquals(job.getJobDefinitionId(), userOperationLogEntry.getJobDefinitionId());
    assertEquals(job.getProcessInstanceId(), userOperationLogEntry.getProcessInstanceId());
    assertEquals(job.getProcessDefinitionId(), userOperationLogEntry.getProcessDefinitionId());
    assertEquals(job.getProcessDefinitionKey(), userOperationLogEntry.getProcessDefinitionKey());
    assertEquals(deploymentId, userOperationLogEntry.getDeploymentId());
    assertEquals(UserOperationLogEntry.CATEGORY_OPERATOR, userOperationLogEntry.getCategory());
  }
}
