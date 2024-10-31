import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { SnackbarProvider } from 'notistack';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorPage from 'pages/error/ErrorPage';
import LoginPage from 'pages/login/LoginPage';
import TablePage from 'pages/table/TablePage';
import DishPage from 'pages/dish/DishPage';
import RestaurantPage from 'pages/restaurant/RestaurantPage';
import DefaultLayout from 'layouts/DefaultLayout';
import EmployeePage from 'pages/employee/EmployeePage';
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
							<Route index element={<LoginPage />} />
							<Route element={<DefaultLayout />}>
								<Route path="/table" element={<TablePage />} />
								<Route path="/dish" element={<DishPage />} />
								<Route path="/employee" element={<EmployeePage />} />
								<Route
									path="/restaurant"
									element={<RestaurantPage />}
								/>
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
