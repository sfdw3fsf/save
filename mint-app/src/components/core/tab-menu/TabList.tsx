import { TabItemProps, TabListProps } from '@/types/tab.type';
import React, { useState } from 'react';
import TabItem from './TabItem';

const TabList: React.FC<TabListProps> = ({ children, activeTabIndex = 0 }) => {
  const [activeTab, setActiveTab] = useState(activeTabIndex);

  const handleTabClick = (index: number) => {
    setActiveTab(index);
  };

  const tabs = React.Children.toArray(children).filter(
    (child): child is React.ReactElement<TabItemProps> => React.isValidElement(child) && child.type === TabItem,
  );

  return (
    <div className='flex lg:flex-row flex-col gap-4 lg:gap-10 w-full  pb-24'>
      <ul
        className='flex lg:flex-col md:flex-nowrap  flex-row p-2 gap-2 lg:max-w-[258px] w-full lg:h-[140px] bg-white rounded-lg border border-slate-200'
        role='tablist'
        aria-orientation='horizontal'>
        {tabs.map((tab, index) => (
          <div className='w-full' key={`tab-${index}`}>
            <button
              role='tab'
              aria-selected={activeTab === index}
              onClick={() => handleTabClick(index)}
              className={`text-xs rounded-lg p-2 flex flex-row gap-1.5 w-full items-center  ${
                activeTab === index
                  ? 'bg-primary-50 text-primary-600'
                  : 'hover:bg-primary-50 active:text-primary-500 hover:text-primary-600 transition-all duration-300 ease-in-out'
              }`}>
              {!!tab.props.rightIcon && (
                <span
                  className={`transition-colors duration-300 ${
                    activeTab === index ? 'text-primary-600' : 'text-slate-500 hover:text-primary-600'
                  }`}>
                  {tab.props.rightIcon}
                </span>
              )}

              {tab.props.label}
            </button>
          </div>
        ))}
      </ul>

      <div className='max-w-[942px] py-8 px-8 h-full  w-full  rounded-xl border border-slate-200 bg-white'>{tabs[activeTab]}</div>
    </div>
  );
};

export default TabList;
