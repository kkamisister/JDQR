import { Stack } from '@mui/material';
import React from 'react';
import DishOptionListItem from './DishOptionListItem';

const DishOptionList = ({
	optionList,
	selectedOption,
	setSelectedOption,
	setIsNew,
}) => {
	return (
		<Stack
			direction="column"
			spacing={1}
			sx={{
				maxHeight: '400px',
				overflowY: 'scroll',
			}}>
			{optionList &&
				optionList.map(option => (
					<DishOptionListItem
						optionName={option.optionName}
						onClick={() => {
							setSelectedOption(option);
							setIsNew(false);
						}}
						key={`dish-option-list-${option.optionId}`}
						selected={selectedOption === option.optionId}
					/>
				))}
		</Stack>
	);
};

export default DishOptionList;
