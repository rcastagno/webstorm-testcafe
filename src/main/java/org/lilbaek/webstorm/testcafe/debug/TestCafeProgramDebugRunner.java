package org.lilbaek.webstorm.testcafe.debug;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.GenericProgramRunner;
import com.intellij.execution.runners.RunContentBuilder;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentDescriptorReusePolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lilbaek.webstorm.testcafe.run.TestCafeCurrentSetup;
import org.lilbaek.webstorm.testcafe.run.TestCafeRunConfiguration;

public class TestCafeProgramDebugRunner extends GenericProgramRunner {

    @Nullable
    @Override
    protected RunContentDescriptor doExecute(RunProfileState state, ExecutionEnvironment environment) throws ExecutionException {
        ExecutionResult result = state.execute(environment.getExecutor(), this);
        RunContentDescriptor descriptor = new RunContentBuilder(result, environment).showRunContent(environment.getContentToReuse());
        return withReusePolicy(descriptor);
    }


    @NotNull
    private static RunContentDescriptor withReusePolicy(@NotNull RunContentDescriptor descriptor) {
        descriptor.setReusePolicy(new RunContentDescriptorReusePolicy() {
            @Override
            public boolean canBeReusedBy(@NotNull RunContentDescriptor newDescriptor) {
                return true;
            }
        });
        return descriptor;
    }

    @NotNull
    @Override
    public String getRunnerId() {
        return "TESTCAFE_WEBSTORM_PROGRAM_DEBUG_RUNNER";
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return DefaultDebugExecutor.EXECUTOR_ID.equals(executorId) && profile instanceof TestCafeRunConfiguration;
    }
}