import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { SnackbarProvider } from 'notistack';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorPage from 'pages/error/ErrorPage';
import LoginPage from 'pages/login/LoginPage';
import HomePage from 'pages/home/HomePage';
import SettingPage from 'pages/settings/SettingPage';
import OrderStatusPage from 'pages/order/OrderStatusPage';
import CreateTablePage from 'pages/settings/table/create/CreateTablePage';
import SettingLayout from 'layouts/SettingLayout';

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
							<Route path="/login" element={<LoginPage />} />
							<Route path="/order" element={<OrderStatusPage />} />
							<Route path="/setting" element={<SettingLayout />}>
								<Route index element={<SettingPage />} />
								<Route path="table">
									<Route path="create" element={<CreateTablePage />} />
									<Route path="edit" element={<LoginPage />} />
								</Route>
								<Route path="restaurant" element={<LoginPage />} />
								<Route path="dish" element={<LoginPage />}>
									<Route path="create" element={<LoginPage />} />
									<Route path="edit" element={<LoginPage />} />
								</Route>
							</Route>
							<Route path="/order" element={<LoginPage />} />
							<Route path="/" element={<HomePage />} />
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
