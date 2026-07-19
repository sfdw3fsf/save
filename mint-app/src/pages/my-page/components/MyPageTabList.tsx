import MyPageTabItem, { MyPageTabItemProps } from './MyPageTabItem';

type MyPageTabListProps = { tabItems: MyPageTabItemProps[] };

const MyPageTabList = ({ tabItems }: MyPageTabListProps): JSX.Element => {
  return (
    <div className='flex h-fit md:flex-col gap-2 p-2 md:max-w-[258px] w-full bg-white border border-comp-border-neutral-soft rounded-lg shadow-regular-shadow-x-small'>
      {tabItems.map((item, index) => (
        <MyPageTabItem key={index} {...item} />
      ))}
    </div>
  );
};

export default MyPageTabList;
