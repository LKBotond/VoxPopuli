import React from "react";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import type { View } from "../../shared/types/View";
import { VIEWS } from "../../shared/types/Constants";
import type { ViewProps } from "../../shared/types/Props";
import { IndexView } from "../../features/exterior/components/IndexView";
import { LoginView } from "../../features/exterior/components/LoginView";
import { RegistrationView } from "../../features/exterior/components/RegistrationView";
import "../../shared/styles/Tailwind.css"
import Main from "../../shared/components/Main";

const queryClient= new QueryClient();
function ExteriorApp() {
  const [view, setView] = React.useState<View>(VIEWS.INDEX);
  const handleViewChange = (newView: View) => {
    setView(newView);
  };
  const renderView = () => {
    const viewProps: ViewProps = { changeViewTo: handleViewChange };

    switch (view) {
      case VIEWS.INDEX:
        return <IndexView {...viewProps} />;
      case VIEWS.LOGIN:
        return <LoginView {...viewProps} />;
      case VIEWS.REGISTER:
        return <RegistrationView {...viewProps} />;
      default:
        return <div>Unknown view</div>;
    }
  };

  return (
    <QueryClientProvider client={queryClient}>
      <Main>{renderView()}</Main>
    </QueryClientProvider>
  )
}

export default ExteriorApp;
