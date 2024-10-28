import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { SnackbarProvider } from 'notistack';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorPage from 'pages/error/ErrorPage';
import LoginPage from 'pages/login/LoginPage';
import MainLayout from 'layouts/MainLayout';
const queryClient = new QueryClient();

function ErrorFallback({ error }) {
	return <ErrorPage message={error.message} />;
}

const App = () => {
	return (
		<ErrorBoundary FallbackComponent={ErrorFallback}>
			<SnackbarProvider autoHideDuration={2000}>
				<QueryClientProvider client={queryClient}>
					<BrowserRouter>
						<Routes>
							{/* <Route path="/" element={<HomePage />} /> */}
							<Route element={<MainLayout />}>
								<Route path="/login" element={<LoginPage />} />
							</Route>
							<Route
								path="*"
								element={
									<ErrorPage message={'페이지를 찾을 수 없습니다'} />
								}
							/>
						</Routes>
					</BrowserRouter>
					<ReactQueryDevtools />
				</QueryClientProvider>
			</SnackbarProvider>
		</ErrorBoundary>
	);
};

export default App;
