'use client';

import React from 'react';
import { Card, Table, Button, Space, Tag, message } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';

interface Category {
  id: number;
  name: string;
  code: string;
  parentId: number | null;
  level: number;
  sort: number;
  status: 'enabled' | 'disabled';
  createTime: string;
}

const mockData: Category[] = [
  {
    id: 1,
    name: '传统文化',
    code: 'traditional-culture',
    parentId: null,
    level: 1,
    sort: 1,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
  {
    id: 2,
    name: '传统节日',
    code: 'traditional-festival',
    parentId: 1,
    level: 2,
    sort: 1,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
  {
    id: 3,
    name: '科技发展',
    code: 'technology',
    parentId: null,
    level: 1,
    sort: 2,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
];

const statusMap = {
  enabled: { text: '启用', color: 'success' },
  disabled: { text: '禁用', color: 'default' },
};

export default function CategoriesPage() {
  const columns: ColumnsType<Category> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '名称',
      dataIndex: 'name',
      ellipsis: true,
    },
    {
      title: '编码',
      dataIndex: 'code',
      width: 180,
    },
    {
      title: '层级',
      dataIndex: 'level',
      width: 80,
    },
    {
      title: '排序',
      dataIndex: 'sort',
      width: 80,
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: 100,
      render: (status: keyof typeof statusMap) => (
        <Tag color={statusMap[status].color}>{statusMap[status].text}</Tag>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 180,
    },
    {
      title: '操作',
      key: 'action',
      width: 120,
      render: (_, record) => (
        <Space size="middle">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          />
          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record)}
          />
        </Space>
      ),
    },
  ];

  const handleAdd = () => {
    message.info('点击了添加按钮');
  };

  const handleEdit = (record: Category) => {
    message.info(`点击了编辑按钮，分类ID：${record.id}`);
  };

  const handleDelete = (record: Category) => {
    message.info(`点击了删除按钮，分类ID：${record.id}`);
  };

  return (
    <div>
      <Card
        title="分类管理"
        extra={
          <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
            新建分类
          </Button>
        }
      >
        <Table
          columns={columns}
          dataSource={mockData}
          rowKey="id"
          pagination={{
            total: mockData.length,
            pageSize: 10,
            showQuickJumper: true,
            showSizeChanger: true,
            showTotal: (total) => `共 ${total} 条`,
          }}
        />
      </Card>
    </div>
  );
} 