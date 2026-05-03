import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { InteriorView } from "../../features/interior/components/InteriorView";
import "../../shared/styles/Tailwind.css"
import Main from "../../shared/components/Main";

const queryClient = new QueryClient();
function InteriorApp() {
  const renderView = () => {
    return <InteriorView />;
  };

  return (
    <QueryClientProvider client={queryClient}>
      <Main>{renderView()}</Main>
    </QueryClientProvider>
  );
}

export default InteriorApp;
