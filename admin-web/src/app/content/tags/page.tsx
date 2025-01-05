'use client';

import React from 'react';
import { Card, Table, Button, Space, Tag as AntTag, message } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';

interface Tag {
  id: number;
  name: string;
  color: string;
  count: number;
  status: 'enabled' | 'disabled';
  createTime: string;
}

const mockData: Tag[] = [
  {
    id: 1,
    name: '春节',
    color: '#f50',
    count: 10,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
  {
    id: 2,
    name: '传统文化',
    color: '#2db7f5',
    count: 15,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
  {
    id: 3,
    name: '发明',
    color: '#87d068',
    count: 8,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
  {
    id: 4,
    name: '科技',
    color: '#108ee9',
    count: 12,
    status: 'enabled',
    createTime: '2024-01-01 12:00:00',
  },
];

const statusMap = {
  enabled: { text: '启用', color: 'success' },
  disabled: { text: '禁用', color: 'default' },
};

export default function TagsPage() {
  const columns: ColumnsType<Tag> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '名称',
      dataIndex: 'name',
      render: (name: string, record) => (
        <AntTag color={record.color}>{name}</AntTag>
      ),
    },
    {
      title: '文章数',
      dataIndex: 'count',
      width: 100,
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: 100,
      render: (status: keyof typeof statusMap) => (
        <AntTag color={statusMap[status].color}>{statusMap[status].text}</AntTag>
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

  const handleEdit = (record: Tag) => {
    message.info(`点击了编辑按钮，标签ID：${record.id}`);
  };

  const handleDelete = (record: Tag) => {
    message.info(`点击了删除按钮，标签ID：${record.id}`);
  };

  return (
    <div>
      <Card
        title="标签管理"
        extra={
          <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
            新建标签
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